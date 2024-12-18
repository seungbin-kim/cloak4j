package io.github.cloak4j;

import io.github.cloak4j.annotation.AutoMasking;
import io.github.cloak4j.annotation.FieldMasking;
import io.github.cloak4j.annotation.IgnoreMasking;
import io.github.cloak4j.annotation.ManualMasking;
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

    /**
     * {@link DefaultHandlerFactory#generateDefaultHandlerSet()} 메서드가 반환하는 기본제공 핸들러를 가진 Masker 생성
     */
    public ObjectMasker() {
        this(DefaultHandlerFactory.generateDefaultHandlerSet());
    }

    /**
     * 인자로 넘겨받은 핸들러만 가진 Masker 생성.</br>
     * 기본제공 핸들러를 가져야 한다면 인자에 포함시키거나 {@link #addHandler(MaskingHandler)} 으로 추가하여야 합니다.
     *
     * @param maskingHandlers {@link MaskingHandler} 인터페이스를 구현한 Handler 집합
     */
    public ObjectMasker(Set<MaskingHandler> maskingHandlers) {
        this.maskingHandlerMap = new HashMap<>();
        maskingHandlers.forEach(handler -> maskingHandlerMap.put(handler.getClass(), handler));
    }

    /**
     * MaskingHandler 추가
     *
     * @param handler {@link MaskingHandler} 인터페이스를 구현한 Handler
     */
    public void addHandler(MaskingHandler handler) {
        this.maskingHandlerMap.put(handler.getClass(), handler);
    }

    /**
     * Masker 내부에 저장된 {@code handlerClass} 인스턴스를 얻습니다.
     *
     * @param handlerClass {@link MaskingHandler} 인터페이스를 구현한 Handler 클래스
     * @param <T>          {@link MaskingHandler} 인터페이스를 구현한 클래스의 타입
     * @return {@code handlerClass} 인스턴스를 가지고 있다면 반환. 없다면 null
     */
    public <T extends MaskingHandler> T getHandler(Class<T> handlerClass) {
        MaskingHandler handler = this.maskingHandlerMap.get(handlerClass);
        if (handler == null) {
            return null;
        }
        //noinspection unchecked
        return (T) handler;
    }

    /**
     * 객체의 필드를 마스킹 처리합니다.</br>
     * 대상 클래스 상단 {@link ManualMasking} 어노테이션 존재시 {@link FieldMasking}(또는 사용자 정의)어노테이션으로
     * 지정한 필드만 마스킹 처리됩니다.</br>
     * </br>
     * 대상 클래스 상단 {@link AutoMasking} 어노테이션 존재시 Masker 인스턴스가 가진 {@link MaskingHandler}를 활용해
     * 자동으로 마스킹 처리합니다.</br>
     * 특정 필드만 {@link FieldMasking}(또는 사용자 정의)어노테이션을 사용하여 마스킹 핸들러를 지정할 수 있습니다.</br>
     * </br>
     * 처리할 마스킹 핸들러가 존재하지 않으면 마스킹되지 않습니다.
     *
     * @param object 마스킹 처리할 필드가 존재하는 객체
     */
    public void mask(Object object) {
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
                String maskedValue = maskValue((String) value, field, autoMaskingChar);
                if (!value.equals(maskedValue)) {
                    field.set(object, maskedValue);
                }
            }
        } catch (IllegalAccessException e) {
            logger.debug("Failed to access field: {} in class {}", field.getName(), object.getClass().getName(), e);
        }
    }

    private String maskValue(String value, Field field, Character autoMaskingChar) {
        return getFieldMaskingAnnotation(field).map(fieldMasking -> {
                    MaskingHandler handler = maskingHandlerMap.get(fieldMasking.handler());
                    return handler != null ? handler.mask(value, fieldMasking.maskingChar()) : value;
                })
                .orElseGet(() -> {
                    if (autoMaskingChar != null) {
                        return maskingHandlerMap.values().stream()
                                .filter(handler -> handler.supports(value))
                                .findFirst()
                                .map(handler -> handler.mask(value, autoMaskingChar))
                                .orElse(value);
                    }
                    return value;
                });
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
