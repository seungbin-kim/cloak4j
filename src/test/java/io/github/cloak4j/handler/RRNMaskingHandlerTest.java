package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RRNMaskingHandlerTest {

    @Test
    void supports_hyphenatedRRN_true_1() {
        // given
        String rrn = "970101-1234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertTrue(supports);
    }

    @Test
    void supports_nonHyphenatedRRN_true_1() {
        // given
        String rrn = "9701011234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertTrue(supports);
    }

    @Test
    void supports_hyphenatedRRN_invalidMonth_false_1() {
        // given
        String rrn = "971301-1234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_nonHyphenatedRRN_invalidMonth_false_1() {
        // given
        String rrn = "9713011234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_hyphenatedRRN_invalidDay_false_1() {
        // given
        String rrn = "971032-1234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        assertFalse(supports);
    }

    @Test
    void supports_nonHyphenatedRRN_invalidDay_false_1() {
        // given
        String rrn = "9710321234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_hyphenatedRRN_invalidLength_false_1() {
        // given
        String rrn = "971031-12345678";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_hyphenatedRRN_invalidLength_false_2() {
        // given
        String rrn = "971031-123456";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_nonHyphenatedRRN_invalidLength_false_1() {
        // given
        String rrn = "97103112345678";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_nonHyphenatedRRN_invalidLength_false_2() {
        // given
        String rrn = "971031123456";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        boolean supports = handler.supports(rrn);

        // then
        assertFalse(supports);
    }

    @Test
    void mask_hyphenatedRRN_valid_1() {
        // given
        String rrn = "970101-1234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals("970101-*******", masked);
    }

    @Test
    void mask_nonHyphenatedRRN_valid_1() {
        // given
        String rrn = "9701011234567";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals("970101*******", masked);
    }

    @Test
    void mask_null() {
        // given
        String rrn = null;

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertNull(masked);
    }

    @Test
    void mask_blank() {
        // given
        String rrn = "   ";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals(rrn, masked);
    }

    @Test
    void mask_hyphenatedRRN_invalidLength_1() {
        // given
        String rrn = "970101-12345678";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals(rrn, masked);
    }

    @Test
    void mask_hyphenatedRRN_invalidLength_2() {
        // given
        String rrn = "970101-123456";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals(rrn, masked);
    }

    @Test
    void mask_nonHyphenatedRRN_invalidLength_1() {
        // given
        String rrn = "97010112345678";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals(rrn, masked);
    }

    @Test
    void mask_nonHyphenatedRRN_invalidLength_2() {
        // given
        String rrn = "970101123456";

        // when
        RRNMaskingHandler handler = new RRNMaskingHandler();
        String masked = handler.mask(rrn, '*');

        // then
        assertEquals(rrn, masked);
    }

}