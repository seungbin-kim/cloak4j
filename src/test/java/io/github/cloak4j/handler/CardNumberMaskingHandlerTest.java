package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberMaskingHandlerTest {

    static final String[][] VALID_CARD_NUMBERS = {
            {"9409-1234-5678-9000", "9409-****-****-****"},
            {"9409123456789000", "9409************"},
            {"3759-123456-78900", "3759-******-*****"},
            {"375912345678900", "3759***********"},
    };

    static final String[] INVALID_CARD_NUMBERS = {
            "9409-1234-5678-90000",
            "94091234567890000",
            "3759-123456-789000",
            "3759-1234567-78900",
            "   ",
            null
    };

    @Test
    void supports_true_1() {
        // given
        for (String[] number : VALID_CARD_NUMBERS) {
            // when
            CardNumberMaskingHandler handler = new CardNumberMaskingHandler();
            boolean supports = handler.supports(number[0]);

            // then
            assertTrue(supports);
        }
    }

    @Test
    void supports_false_1() {
        // given
        for (String number : INVALID_CARD_NUMBERS) {
            // when
            CardNumberMaskingHandler handler = new CardNumberMaskingHandler();
            boolean supports = handler.supports(number);

            // then
            assertFalse(supports);
        }
    }

    @Test
    void mask_valid_1() {
        // given
        for (String[] number : VALID_CARD_NUMBERS) {
            // when
            CardNumberMaskingHandler handler = new CardNumberMaskingHandler();
            String masked = handler.mask(number[0], '*');

            // then
            assertEquals(number[1], masked);
        }
    }

    @Test
    void mask_invalid_1() {
        // given
        for (String number : INVALID_CARD_NUMBERS) {
            // when
            CardNumberMaskingHandler handler = new CardNumberMaskingHandler();
            String masked = handler.mask(number, '*');

            // then
            assertEquals(number, masked);
        }
    }

}