package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RRNMaskingHandlerTest {

    static final String[][] VALID_RRN = {
            {"970101-1234567", "970101-*******"},
            {"9701011234567", "970101*******"}
    };

    static final String[] INVALID_RRN = {
            "971301-1234567",
            "9713011234567",
            "971032-1234567",
            "9710321234567",
            "971031-12345678",
            "971031-123456",
            "97103112345678",
            "971031123456",
            "   ",
            null

    };

    @Test
    void supports_true_1() {
        // given
        for (String[] rrn : VALID_RRN) {
            // when
            RRNMaskingHandler handler = new RRNMaskingHandler();
            boolean supports = handler.supports(rrn[0]);

            // then
            assertTrue(supports);
        }
    }

    @Test
    void supports_false_1() {
        // given
        for (String rrn : INVALID_RRN) {
            // when
            RRNMaskingHandler handler = new RRNMaskingHandler();
            boolean supports = handler.supports(rrn);

            // then
            assertFalse(supports);
        }
    }

    @Test
    void mask_valid_1() {
        // given
        for (String[] rrn : VALID_RRN) {
            // when
            RRNMaskingHandler handler = new RRNMaskingHandler();
            String masked = handler.mask(rrn[0], '*');

            // then
            assertEquals(rrn[1], masked);
        }
    }

    @Test
    void mask_invalid_1() {
        // given
        for (String rrn : INVALID_RRN) {
            // when
            RRNMaskingHandler handler = new RRNMaskingHandler();
            String masked = handler.mask(rrn, '*');

            // then
            assertEquals(rrn, masked);
        }
    }

}