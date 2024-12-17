package io.github.cloak4j;

import io.github.cloak4j.handler.DefaultHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class ObjectMasker {

    private static final Logger logger = LoggerFactory.getLogger(ObjectMasker.class);

    private final Map<Class<? extends MaskingHandler>, MaskingHandler> maskingHandlerMap;

    public ObjectMasker() {
        this(DefaultHandlerFactory.getDefaultHandlerSet());
    }

    public ObjectMasker(Set<MaskingHandler> maskingHandlers) {
        this.maskingHandlerMap = new HashMap<>();
        maskingHandlers.forEach(handler -> maskingHandlerMap.put(handler.getClass(), handler));
    }

    public void addHandler(MaskingHandler handler) {
        this.maskingHandlerMap.put(handler.getClass(), handler);
    }

    public <T extends MaskingHandler> T getHandler(Class<T> handlerClass) {
        MaskingHandler handler = this.maskingHandlerMap.get(handlerClass);
        if (handler == null) {
            return null;
        }
        //noinspection unchecked
        return (T) handler;
    }

    public <T> void mask(T object) {
        Class<?> clazz = object.getClass();
        if (clazz.isAnnotationPresent(ManualMasking.class)) {
            maskFields(object, this::shouldMaskManually, null);
        } else {
            AutoMasking autoMasking = clazz.getDeclaredAnnotation(AutoMasking.class);
            if (autoMasking != null) {
                maskFields(object, not(field -> field.isAnnotationPresent(IgnoreMasking.class)), autoMasking.maskingChar());
            }
        }
    }

    private <T> void maskFields(T object, Predicate<Field> shouldMask, Character autoMaskingChar) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (shouldMask.test(field)) {
                maskField(object, field, autoMaskingChar);
            }
        }
    }

    private <T> void maskField(T object, Field field, Character autoMaskingChar) {
        try {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value == null) return;

            if (isObjectField(field)) {
                mask(value);
            } else if (value instanceof String) {
                String maskedValue = (autoMaskingChar != null) ?
                        maskValueAutomatically((String) value, autoMaskingChar) :
                        maskValue((String) value, field);
                if (!value.equals(maskedValue)) {
                    field.set(object, maskedValue);
                }
            }
        } catch (IllegalAccessException e) {
            logger.debug("Failed to access field: {} in class {}", field.getName(), object.getClass().getName(), e);
        }
    }

    private String maskValueAutomatically(String value, char autoMaskingChar) {
        return maskingHandlerMap.values().stream()
                .filter(handler -> handler.supports(value))
                .findFirst()
                .map(handler -> handler.mask(value, autoMaskingChar))
                .orElse(value);
    }

    private String maskValue(String value, Field field) {
        return getFieldMaskingAnnotation(field)
                .map(fieldMasking -> {
                    MaskingHandler handler = maskingHandlerMap.get(fieldMasking.handler());
                    return handler != null ? handler.mask(value, fieldMasking.maskingChar()) : value;
                })
                .orElse(value);
    }

    private boolean shouldMaskManually(Field field) {
        return !field.isAnnotationPresent(IgnoreMasking.class) &&
                (isObjectField(field) || getFieldMaskingAnnotation(field).isPresent());
    }

    private Optional<FieldMasking> getFieldMaskingAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof FieldMasking fieldMasking) {
                return Optional.of(fieldMasking);
            }
            FieldMasking fieldMasking = annotation.annotationType().getAnnotation(FieldMasking.class);
            if (fieldMasking != null) {
                return Optional.of(fieldMasking);
            }
        }
        return Optional.empty();
    }

    private boolean isObjectField(Field field) {
        return !field.getType().isPrimitive() && field.getType() != String.class;
    }
}
