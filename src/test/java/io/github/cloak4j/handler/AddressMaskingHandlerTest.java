package io.github.cloak4j.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressMaskingHandlerTest {

    static final String[][] ADDRESSES = {
            {"대전광역시 동구", "대전광역시 동구"},
            {"부산광역시 해운대구 해운대해변로 140", "부산광역시 해운대구 ****** ***"},
            {"대구광역시 달서구 상인동 123", "대구광역시 달서구 *** ***"},
            {"경기도 고양시 일산동구 중앙로 123", "경기도 고양시 **** *** ***"},
            {"인천광역시 연수구 송도동 123", "인천광역시 연수구 *** ***"},
            {"경기도 안양시 동안구 평촌대로 123", "경기도 안양시 *** **** ***"},
            {"인천광역시 남동구 구월동 123-4", "인천광역시 남동구 *** *****"},
            {"경기도 성남시 분당구 판교로 123", "경기도 성남시 *** *** ***"},
            {"대전광역시 유성구 봉명동 123", "대전광역시 유성구 *** ***"},
            {"제주특별자치도 제주시 애월읍 123", "제주특별자치도 제주시 *** ***"},
            {"세종특별자치시 조치원읍 123", "세종특별자치시 **** ***"},
    };

    @Test
    void supports_true_1() {
        // given
        for (String[] address : ADDRESSES) {
            // when
            AddressMaskingHandler handler = new AddressMaskingHandler();
            boolean supports = handler.supports(address[0]);

            // then
            assertTrue(supports);
        }
    }

    @Test
    void supports_false_1() {
        // given
        String address = "123";

        // when
        AddressMaskingHandler handler = new AddressMaskingHandler();
        boolean supports = handler.supports(address);

        // then
        assertFalse(supports);
    }

    @Test
    void supports_valid_1() {
        // given
        for (String[] address : ADDRESSES) {
            // when
            AddressMaskingHandler handler = new AddressMaskingHandler();
            String masked = handler.mask(address[0], '*');

            // then
            assertEquals(address[1], masked);
        }
    }

    @Test
    void supports_invalid_1() {
        // given
        String address = "123";

        // when
        AddressMaskingHandler handler = new AddressMaskingHandler();
        String masked = handler.mask(address, '*');

        // then
        assertEquals(address, masked);
    }

}