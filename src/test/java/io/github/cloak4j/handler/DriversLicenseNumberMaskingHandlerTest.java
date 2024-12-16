package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriversLicenseNumberMaskingHandlerTest {

    @Test
    void supports_true_1() {
        // given
        String dln = "11-11-123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        boolean supports = handler.supports(dln);

        // then
        assertTrue(supports);
    }

    @Test
    void supports_false_1() {
        // given
        String dln = "111-11-123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        boolean supports = handler.supports(dln);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_false_2() {
        // given
        String dln = "11-111-123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        boolean supports = handler.supports(dln);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_false_3() {
        // given
        String dln = "11-11-1123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        boolean supports = handler.supports(dln);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_false_4() {
        // given
        String dln = "11-11-123456-111";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        boolean supports = handler.supports(dln);

        // then
        assertFalse(supports);
    }

    @Test
    void mask_valid_1() {
        // given
        String dln = "11-11-123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals("11-11-******-**", masked);
    }

    @Test
    void mask_invalid_1() {
        // given
        String dln = "111-11-123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals(dln, masked);
    }

    @Test
    void mask_invalid_2() {
        // given
        String dln = "11-111-123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals(dln, masked);
    }

    @Test
    void mask_invalid_3() {
        // given
        String dln = "11-11-1123456-11";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals(dln, masked);
    }

    @Test
    void mask_invalid_4() {
        // given
        String dln = "11-11-123456-111";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals(dln, masked);
    }

    @Test
    void mask_null() {
        // given
        String dln = null;

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals(dln, masked);
    }

    @Test
    void mask_blank() {
        // given
        String dln = "   ";

        // when
        DriversLicenseNumberMaskingHandler handler = new DriversLicenseNumberMaskingHandler();
        String masked = handler.mask(dln, '*');

        // then
        assertEquals(dln, masked);
    }

}