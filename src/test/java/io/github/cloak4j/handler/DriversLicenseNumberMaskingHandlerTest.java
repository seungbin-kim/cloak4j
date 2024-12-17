package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriversLicenseNumberMaskingHandlerTest {

    static final String[][] VALID_LICENSE_NUMBERS = {
            {"11-11-123456-11", "11-11-******-**"}
    };

    static final String[] INVALID_LICENSE_NUMBERS = {
            "111-11-123456-11",
            "11-111-123456-11",
            "11-11-1123456-11",
            "11-11-123456-111",
            "   ",
            null,
    };

    @Test
    void supports_true_1() {
        // given
        for (String[] number : VALID_LICENSE_NUMBERS) {
            // when
            DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
            boolean supports = handler.supports(number[0]);

            // then
            assertTrue(supports);
        }
    }

    @Test
    void supports_false_1() {
        // given
        for (String number : INVALID_LICENSE_NUMBERS) {
            // when
            DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
            boolean supports = handler.supports(number);

            // then
            assertFalse(supports);
        }
    }

    @Test
    void mask_valid_1() {
        // given
        for (String[] number : VALID_LICENSE_NUMBERS) {
            // when
            DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
            String masked = handler.mask(number[0], '*');

            // then
            assertEquals(number[1], masked);
        }
    }

    @Test
    void mask_invalid_1() {
        // given
        for (String number : INVALID_LICENSE_NUMBERS) {
            // when
            DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
            String masked = handler.mask(number, '*');

            // then
            assertEquals(number, masked);
        }
    }

}