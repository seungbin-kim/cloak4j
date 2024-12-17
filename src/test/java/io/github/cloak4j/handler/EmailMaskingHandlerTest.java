package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailMaskingHandlerTest {

    static final String[][] VALID_EMAIL_ADDRESSES = {
            {"a@abc.com", "*@abc.com"},
            {"ab@abc.com", "a*@abc.com"},
            {"abc.def-g@abc.com", "ab*******@abc.com"},
            {"bac_d.e@abc.com", "ba*****@abc.com"},
    };

    static final String[] INVALID_EMAIL_ADDRESSES = {
            "@abc.com",
            "abc@abc.c",
            "abc.com",
            "abc@abc",
            "abc",
            "   ",
            null,
    };

    @Test
    void supports_ture_1() {
        // given
        for (String[] email : VALID_EMAIL_ADDRESSES) {
            // when
            EmailMaskingHandler handler = new EmailMaskingHandler();
            boolean supports = handler.supports(email[0]);

            // then
            assertTrue(supports);
        }
    }

    @Test
    void supports_false_1() {
        // given
        for (String email : INVALID_EMAIL_ADDRESSES) {
            // when
            EmailMaskingHandler handler = new EmailMaskingHandler();
            boolean supports = handler.supports(email);

            // then
            assertFalse(supports);
        }
    }

    @Test
    void mask_valid_1() {
        // given
        for (String[] email : VALID_EMAIL_ADDRESSES) {
            // when
            EmailMaskingHandler handler = new EmailMaskingHandler();
            String masked = handler.mask(email[0], '*');

            // then
            assertEquals(email[1], masked);
        }
    }

    @Test
    void mask_invalid_1() {
        // given
        for (String email : INVALID_EMAIL_ADDRESSES) {
            // when
            EmailMaskingHandler handler = new EmailMaskingHandler();
            String masked = handler.mask(email, '*');

            // then
            assertEquals(email, masked);
        }
    }

}