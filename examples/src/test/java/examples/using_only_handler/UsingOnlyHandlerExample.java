package examples.using_only_handler;

import io.github.cloak4j.MaskingHandler;
import io.github.cloak4j.handler.*;
import org.junit.jupiter.api.Test;

public class UsingOnlyHandlerExample {

    @Test
    void useHandler_rrn_example() {
        MaskingHandler handler = new RRNMaskingHandler();

        String masked1 = handler.mask("970101-1234567", '*');
        String masked2 = handler.mask("9701011234567", '*');

        System.out.println(masked1); // 970101-*******
        System.out.println(masked2); // 970101*******
    }

    @Test
    void useHandler_driversLicense_example() {
        MaskingHandler handler = new DriversLicenseNumberMaskingHandler();

        String masked1 = handler.mask("11-11-111111-11", '*');

        System.out.println(masked1); // 11-11-******-**
    }

    @Test
    void useHandler_email_example() {
        MaskingHandler handler = new EmailMaskingHandler();

        String masked1 = handler.mask("a@abc.com", '*');
        String masked2 = handler.mask("my-email@gmail.com", '*');

        System.out.println(masked1); // *@abc.com
        System.out.println(masked2); // my******@gmail.com
    }

    @Test
    void useHandler_cdno_example() {
        MaskingHandler handler = new CardNumberMaskingHandler();

        String masked1 = handler.mask("9409-1234-5678-9000", '*');
        String masked2 = handler.mask("9409123456789000", '*');
        String masked3 = handler.mask("3759-123456-78900", '*');
        String masked4 = handler.mask("375912345678900", '*');

        System.out.println(masked1); // 9409-****-****-****
        System.out.println(masked2); // 9409************
        System.out.println(masked3); // 3759-******-*****
        System.out.println(masked4); // 3759***********
    }

    @Test
    void useHandler_address_example() {
        MaskingHandler handler = new AddressMaskingHandler();

        String masked1 = handler.mask("제주특별자치도 제주시 애월읍 123", '*');
        String masked2 = handler.mask("대전광역시 유성구 봉명동 123", '*');
        String masked3 = handler.mask("경기도 성남시 분당구 판교로 123", '*');
        String masked4 = handler.mask("세종특별자치시 조치원읍 123", '*');
        String masked5 = handler.mask("서울특별시 동대문구 놀러오세로 123", '*');

        System.out.println(masked1); // 제주특별자치도 제주시 *** ***
        System.out.println(masked2); // 대전광역시 유성구 *** ***
        System.out.println(masked3); // 경기도 성남시 *** *** ***
        System.out.println(masked4); // 세종특별자치시 **** ***
        System.out.println(masked5); // 서울특별시 동대문구 ***** ***
    }

    @Test
    void useHandler_phoneNumber_example() {
        MaskingHandler handler = new PhoneNumberMaskingHandler();

        String masked1 = handler.mask("010-1234-5678", '*');
        String masked2 = handler.mask("01012345678", '*');
        String masked3 = handler.mask("070-1234-5678", '*');
        String masked4 = handler.mask("07012345678", '*');
        String masked5 = handler.mask("02-123-5678", '*');
        String masked6 = handler.mask("021235678", '*');
        String masked7 = handler.mask("02-1234-5678", '*');
        String masked8 = handler.mask("0212345678", '*');
        String masked9 = handler.mask("042-1234-5678", '*');
        String masked0 = handler.mask("04212345678", '*');

        System.out.println(masked1); // 010-****-5678
        System.out.println(masked2); // 010****5678
        System.out.println(masked3); // 070-****-5678
        System.out.println(masked4); // 070****5678
        System.out.println(masked5); // 02-***-5678
        System.out.println(masked6); // 02***5678
        System.out.println(masked7); // 02-****-5678
        System.out.println(masked8); // 02****5678
        System.out.println(masked9); // 042-****-5678
        System.out.println(masked0); // 042****5678
    }

}
