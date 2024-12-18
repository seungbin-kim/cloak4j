package io.github.cloak4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMaskerTest {

    @Test
    void mask_validStartIndex_1() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 0, 5, '*');

        // then
        assertEquals("***** World", masked);
    }

    @Test
    void mask_validStartIndex_2() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 1, 5, '*');

        // then
        assertEquals("H*****World", masked);
    }

    @Test
    void mask_validStartIndex_3() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 6, 5, '*');

        // then
        assertEquals("Hello *****", masked);
    }

    @Test
    void mask_excessMaskingLength_1() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 6, 6, '*');

        // then
        assertEquals("Hello *****", masked);
    }

    @Test
    void mask_excessMaskingLength_2() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 7, 10, '*');

        // then
        assertEquals("Hello W****", masked);
    }

    @Test
    void mask_excessMaskingLength_3() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 10, 10, '*');

        // then
        assertEquals("Hello Worl*", masked);
    }

    @Test
    void mask_invalidStartIndex_1() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, -1, 5, '*');

        // then
        assertEquals("Hello World", masked);
    }

    @Test
    void mask_invalidStartIndex_2() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 100, 5, '*');

        // then
        assertEquals("Hello World", masked);
    }

    @Test
    void mask_invalidMaskingLength_1() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 0, -1, '*');

        // then
        assertEquals("Hello World", masked);
    }

    @Test
    void mask_null() {
        // given
        String target = null;

        // when
        String masked = SimpleMasker.mask(target, 0, 1, '*');

        // then
        assertNull(masked);
    }

    @Test
    void mask_blank() {
        // given
        String target = "   ";

        // when
        String masked = SimpleMasker.mask(target, 0, 1, '*');

        // then
        assertEquals(target, masked);
    }

    @Test
    void mask_overloading_startIndex_1() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 6, '*');

        // then
        assertEquals("Hello *****", masked);
    }

    @Test
    void mask_overloading_startIndex_2() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 0, '*');

        // then
        assertEquals("***********", masked);
    }

    @Test
    void mask_overloading_startIndex_3() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, 100, '*');

        // then
        assertEquals("Hello World", masked);
    }

    @Test
    void mask_overloading_endIndex_1() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, '*', 10);

        // then
        assertEquals("**********d", masked);
    }

    @Test
    void mask_overloading_endIndex_2() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, '*', 11);

        // then
        assertEquals("***********", masked);
    }

    @Test
    void mask_overloading_endIndex_3() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, '*', 6);

        // then
        assertEquals("******World", masked);
    }

    @Test
    void mask_overloading_endIndex_4() {
        // given
        String target = "Hello World";

        // when
        String masked = SimpleMasker.mask(target, '*', 100);

        // then
        assertEquals("***********", masked);
    }

}