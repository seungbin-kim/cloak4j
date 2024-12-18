package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberMaskingHandlerTest {

    static final String[][] VALID_PHONE_NUMBERS = {
            {"010-1234-5678", "010-****-5678"},
            {"01012345678", "010****5678"},
            {"070-1234-5678", "070-****-5678"},
            {"07012345678", "070****5678"},
            {"02-123-5678", "02-***-5678"},
            {"021235678", "02***5678"},
            {"02-1234-5678", "02-****-5678"},
            {"0212345678", "02****5678"},
            {"042-1234-5678", "042-****-5678"},
            {"04212345678", "042****5678"},
    };

    static final String[] INVALID_PHONE_NUMBERS = {
            "070-12345-5678",
            "02-123-56789",
            "02-1234-56798",
            "042-1234-56789",
            "042-123-56789",
            "010-1234-56781",
            "   ",
            null,
    };

    @Test
    void supports_true_1() {
        // given
        for (String[] number : VALID_PHONE_NUMBERS) {
            // when
            PhoneNumberMaskingHandler handler = new PhoneNumberMaskingHandler();
            boolean supports = handler.supports(number[0]);

            // then
            assertTrue(supports);
        }
    }

    @Test
    void supports_false_1() {
        // given
        for (String number : INVALID_PHONE_NUMBERS) {
            // when
            PhoneNumberMaskingHandler handler = new PhoneNumberMaskingHandler();
            boolean supports = handler.supports(number);

            // then
            assertFalse(supports);
        }
    }

    @Test
    void mask_valid_1() {
        // given
        for (String[] number : VALID_PHONE_NUMBERS) {
            // when
            PhoneNumberMaskingHandler handler = new PhoneNumberMaskingHandler();
            String masked = handler.mask(number[0], '*');

            // then
            assertEquals(number[1], masked);
        }
    }

    @Test
    void mask_invalid_1() {
        // given
        for (String number : INVALID_PHONE_NUMBERS) {
            // when
            PhoneNumberMaskingHandler handler = new PhoneNumberMaskingHandler();
            String masked = handler.mask(number, '*');

            // then
            assertEquals(number, masked);
        }
    }

}