package io.github.cloak4j;

import io.github.cloak4j.handler.PhoneNumberMaskingHandler;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMaskerTest {

    @Test
    void constructor_1() {
        // given
        Set<MaskingHandler> defaultHandlerSet = DefaultHandlerFactory.getDefaultHandlerSet();

        // when
        ObjectMasker objectMasker = new ObjectMasker(defaultHandlerSet);

        // then
        for (MaskingHandler handler : defaultHandlerSet) {
            MaskingHandler maskingHandler = objectMasker.getHandler(handler.getClass());
            assertEquals(handler, maskingHandler);
        }
    }

    @Test
    void addHandler_1() {
        // given
        MaskingHandler maskingHandler = new MaskingHandler() {
            @Override
            public String mask(String in, char maskChar) {
                return "";
            }

            @Override
            public boolean supports(String in) {
                return false;
            }
        };

        // when
        ObjectMasker objectMasker = new ObjectMasker(Set.of());
        objectMasker.addHandler(maskingHandler);

        // then
        MaskingHandler handler = objectMasker.getHandler(maskingHandler.getClass());
        assertEquals(maskingHandler, handler);
    }

    @Test
    void getHandler_1() {
        // given
        MaskingHandler maskingHandler = new MaskingHandler() {
            @Override
            public String mask(String in, char maskChar) {
                return "";
            }

            @Override
            public boolean supports(String in) {
                return false;
            }
        };
        ObjectMasker objectMasker = new ObjectMasker(Set.of(maskingHandler));

        // when
        MaskingHandler handler = objectMasker.getHandler(maskingHandler.getClass());

        // then
        assertEquals(maskingHandler, handler);
    }

    @Test
    void getHandler_2() {
        // given
        MaskingHandler maskingHandler = new MaskingHandler() {
            @Override
            public String mask(String in, char maskChar) {
                return "";
            }

            @Override
            public boolean supports(String in) {
                return false;
            }
        };
        ObjectMasker objectMasker = new ObjectMasker();

        // when
        MaskingHandler handler = objectMasker.getHandler(maskingHandler.getClass());

        // then
        assertNull(handler);
    }



    @Test
    void mask_manual_1() {
        // given
        @ManualMasking
        final class TestObject {
            private String nonMaskingField;
            @FieldMasking(handler = PhoneNumberMaskingHandler.class)
            private String maskingField;
            private TestInnerObject object;
            private Long longField = 0L;
            private boolean booleanField;
            private int intField;
            private TestInnerObject nullObject;

            @ManualMasking
            static final class TestInnerObject {
                @IgnoreMasking
                private String nonMaskingField;
                @FieldMasking(handler = PhoneNumberMaskingHandler.class, maskingChar = '#')
                private String maskingField;
            }
        }

        String phoneNumber1 = "02-123-4567";
        String phoneNumber2 = "010-1234-5678";
        String maskedPhoneNumber1 = "02-***-4567";
        String maskedPhoneNumber2 = "010-####-5678";

        TestObject.TestInnerObject innerObject = new TestObject.TestInnerObject();
        innerObject.nonMaskingField = phoneNumber2;
        innerObject.maskingField = phoneNumber2;

        TestObject targetObject = new TestObject();
        targetObject.nonMaskingField = phoneNumber1;
        targetObject.maskingField = phoneNumber1;
        targetObject.object = innerObject;

        // when
        ObjectMasker objectMasker = new ObjectMasker();
        objectMasker.mask(targetObject);

        // then
        assertEquals(phoneNumber1, targetObject.nonMaskingField);
        assertEquals(maskedPhoneNumber1, targetObject.maskingField);
        assertEquals(phoneNumber2, targetObject.object.nonMaskingField);
        assertEquals(maskedPhoneNumber2, targetObject.object.maskingField);
        assertEquals(0L, targetObject.longField);
        assertFalse(targetObject.booleanField);
        assertEquals(0, targetObject.intField);
        assertNull(targetObject.nullObject);
    }

    @Test
    void mask_manualWithAuto_1() {
        // given
        @ManualMasking
        final class TestObject {
            private String nonMaskingField;
            @FieldMasking(handler = PhoneNumberMaskingHandler.class)
            private String maskingField;
            private TestInnerObject object;
            private Long longField = 0L;
            private boolean booleanField;
            private int intField;
            private TestInnerObject nullObject;

            @AutoMasking(maskingChar = '#')
            static final class TestInnerObject {
                @IgnoreMasking
                private String nonMaskingField;
                private String maskingField;
            }
        }

        String phoneNumber1 = "02-123-4567";
        String phoneNumber2 = "010-1234-5678";
        String maskedPhoneNumber1 = "02-***-4567";
        String maskedPhoneNumber2 = "010-####-5678";

        TestObject.TestInnerObject innerObject = new TestObject.TestInnerObject();
        innerObject.nonMaskingField = phoneNumber2;
        innerObject.maskingField = phoneNumber2;

        TestObject targetObject = new TestObject();
        targetObject.nonMaskingField = phoneNumber1;
        targetObject.maskingField = phoneNumber1;
        targetObject.object = innerObject;

        // when
        ObjectMasker objectMasker = new ObjectMasker();
        objectMasker.mask(targetObject);

        // then
        assertEquals(phoneNumber1, targetObject.nonMaskingField);
        assertEquals(maskedPhoneNumber1, targetObject.maskingField);
        assertEquals(phoneNumber2, targetObject.object.nonMaskingField);
        assertEquals(maskedPhoneNumber2, targetObject.object.maskingField);
        assertEquals(0L, targetObject.longField);
        assertFalse(targetObject.booleanField);
        assertEquals(0, targetObject.intField);
        assertNull(targetObject.nullObject);
    }

    @Test
    void mask_auto_1() {
        // given
        @AutoMasking
        final class TestObject {
            @IgnoreMasking
            private String nonMaskingField;
            private String maskingField;
            private TestInnerObject object;
            private Long longField = 0L;
            private boolean booleanField;
            private int intField;
            private TestInnerObject nullObject;

            @AutoMasking(maskingChar = '#')
            static final class TestInnerObject {
                @IgnoreMasking
                private String nonMaskingField;
                private String maskingField;
            }
        }

        String phoneNumber1 = "02-123-4567";
        String phoneNumber2 = "010-1234-5678";
        String maskedPhoneNumber1 = "02-***-4567";
        String maskedPhoneNumber2 = "010-####-5678";

        TestObject.TestInnerObject innerObject = new TestObject.TestInnerObject();
        innerObject.nonMaskingField = phoneNumber2;
        innerObject.maskingField = phoneNumber2;

        TestObject targetObject = new TestObject();
        targetObject.nonMaskingField = phoneNumber1;
        targetObject.maskingField = phoneNumber1;
        targetObject.object = innerObject;

        // when
        ObjectMasker objectMasker = new ObjectMasker();
        objectMasker.mask(targetObject);

        // then
        assertEquals(phoneNumber1, targetObject.nonMaskingField);
        assertEquals(maskedPhoneNumber1, targetObject.maskingField);
        assertEquals(phoneNumber2, targetObject.object.nonMaskingField);
        assertEquals(maskedPhoneNumber2, targetObject.object.maskingField);
        assertEquals(0L, targetObject.longField);
        assertFalse(targetObject.booleanField);
        assertEquals(0, targetObject.intField);
        assertNull(targetObject.nullObject);
    }

    @Test
    void mask_auto_overriding_1() {
        // given
        @AutoMasking
        final class TestObject {
            @IgnoreMasking
            private String nonMaskingField;
            @FieldMasking(handler = PhoneNumberMaskingHandler.class, maskingChar = '@')
            private String maskingField;
            private TestInnerObject object;
            private Long longField = 0L;
            private boolean booleanField;
            private int intField;
            private TestInnerObject nullObject;

            @AutoMasking(maskingChar = '#')
            static final class TestInnerObject {
                @IgnoreMasking
                private String nonMaskingField;
                @FieldMasking(handler = PhoneNumberMaskingHandler.class, maskingChar = '@')
                private String maskingField;
            }
        }

        String phoneNumber1 = "02-123-4567";
        String phoneNumber2 = "010-1234-5678";
        String maskedPhoneNumber1 = "02-@@@-4567";
        String maskedPhoneNumber2 = "010-@@@@-5678";

        TestObject.TestInnerObject innerObject = new TestObject.TestInnerObject();
        innerObject.nonMaskingField = phoneNumber2;
        innerObject.maskingField = phoneNumber2;

        TestObject targetObject = new TestObject();
        targetObject.nonMaskingField = phoneNumber1;
        targetObject.maskingField = phoneNumber1;
        targetObject.object = innerObject;

        // when
        ObjectMasker objectMasker = new ObjectMasker();
        objectMasker.mask(targetObject);

        // then
        assertEquals(phoneNumber1, targetObject.nonMaskingField);
        assertEquals(maskedPhoneNumber1, targetObject.maskingField);
        assertEquals(phoneNumber2, targetObject.object.nonMaskingField);
        assertEquals(maskedPhoneNumber2, targetObject.object.maskingField);
        assertEquals(0L, targetObject.longField);
        assertFalse(targetObject.booleanField);
        assertEquals(0, targetObject.intField);
        assertNull(targetObject.nullObject);
    }

    @Test
    void mask_autoWithManual_1() {
        // given
        @AutoMasking
        final class TestObject {
            @IgnoreMasking
            private String nonMaskingField;
            private String maskingField;
            private TestInnerObject object;
            private Long longField = 0L;
            private boolean booleanField;
            private int intField;
            private TestInnerObject nullObject;

            @ManualMasking
            static final class TestInnerObject {
                private String nonMaskingField;
                @FieldMasking(handler = PhoneNumberMaskingHandler.class, maskingChar = '#')
                private String maskingField;
            }
        }

        String phoneNumber1 = "02-123-4567";
        String phoneNumber2 = "010-1234-5678";
        String maskedPhoneNumber1 = "02-***-4567";
        String maskedPhoneNumber2 = "010-####-5678";

        TestObject.TestInnerObject innerObject = new TestObject.TestInnerObject();
        innerObject.nonMaskingField = phoneNumber2;
        innerObject.maskingField = phoneNumber2;

        TestObject targetObject = new TestObject();
        targetObject.nonMaskingField = phoneNumber1;
        targetObject.maskingField = phoneNumber1;
        targetObject.object = innerObject;

        // when
        ObjectMasker objectMasker = new ObjectMasker();
        objectMasker.mask(targetObject);

        // then
        assertEquals(phoneNumber1, targetObject.nonMaskingField);
        assertEquals(maskedPhoneNumber1, targetObject.maskingField);
        assertEquals(phoneNumber2, targetObject.object.nonMaskingField);
        assertEquals(maskedPhoneNumber2, targetObject.object.maskingField);
        assertEquals(0L, targetObject.longField);
        assertFalse(targetObject.booleanField);
        assertEquals(0, targetObject.intField);
        assertNull(targetObject.nullObject);
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @FieldMasking(handler = PhoneNumberMaskingHandler.class, maskingChar = '@')
    @interface PhoneNumberMasking {}
    @Test
    void mask_customAnnotation_1() {
        // given
        @ManualMasking
        final class TestObject {
            private String nonMaskingField;
            @PhoneNumberMasking
            private String maskingField;
            private TestInnerObject object;
            private Long longField = 0L;
            private boolean booleanField;
            private int intField;
            private TestInnerObject nullObject;

            @AutoMasking(maskingChar = '#')
            static final class TestInnerObject {
                @IgnoreMasking
                private String nonMaskingField;
                private String maskingField;
            }
        }

        String phoneNumber1 = "02-123-4567";
        String phoneNumber2 = "010-1234-5678";
        String maskedPhoneNumber1 = "02-@@@-4567";
        String maskedPhoneNumber2 = "010-####-5678";

        TestObject.TestInnerObject innerObject = new TestObject.TestInnerObject();
        innerObject.nonMaskingField = phoneNumber2;
        innerObject.maskingField = phoneNumber2;

        TestObject targetObject = new TestObject();
        targetObject.nonMaskingField = phoneNumber1;
        targetObject.maskingField = phoneNumber1;
        targetObject.object = innerObject;

        // when
        ObjectMasker objectMasker = new ObjectMasker();
        objectMasker.mask(targetObject);

        // then
        assertEquals(phoneNumber1, targetObject.nonMaskingField);
        assertEquals(maskedPhoneNumber1, targetObject.maskingField);
        assertEquals(phoneNumber2, targetObject.object.nonMaskingField);
        assertEquals(maskedPhoneNumber2, targetObject.object.maskingField);
        assertEquals(0L, targetObject.longField);
        assertFalse(targetObject.booleanField);
        assertEquals(0, targetObject.intField);
        assertNull(targetObject.nullObject);
    }

}