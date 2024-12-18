package examples.object_masking.custom_annotation;

import io.github.cloak4j.annotation.FieldMasking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldMasking(handler = CustomExampleMaskingHandler.class, maskingChar = '?') // 라이브러리 어노테이션을 메타 어노테이션으로 활용
public @interface MaskingAllWithQuestionMark {
}
