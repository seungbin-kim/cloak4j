package examples.object_masking.custom_annotation;

import io.github.cloak4j.annotation.FieldMasking;
import io.github.cloak4j.handler.CardNumberMaskingHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldMasking(handler = CardNumberMaskingHandler.class, maskingChar = '&')
public @interface MaskingCardNumber {
}