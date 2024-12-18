package examples.object_masking.auto_masking;

import examples.object_masking.custom_annotation.CustomExampleMaskingHandler;
import io.github.cloak4j.ObjectMasker;
import org.junit.jupiter.api.Test;

public class AutoMaskingExample {

    @Test
    void objectMask_autoMasking_example() {
        A testObject = new A();
        testObject.rrn = "970101-1234567";
        testObject.nonMaskedString = "970101-1234567";
        testObject.driversLicenseNumber = "11-11-123456-11";
        testObject.phoneNumber = "010-1234-5678";
        testObject.bObject = new A.B();
        testObject.bObject.email = "test@test.com";
        testObject.bObject.cardNumber = "9409-1234-5678-9999";
        testObject.bObject.address = "서울특별시 동대문구 놀러오세로 123";
        testObject.bObject.customMasked = "이 문자열은 모두 마스킹 될 것입니다.";

        ObjectMasker masker = new ObjectMasker(); // 기본 생성자는 기본제공 핸들러들을 모두 가지게 됩니다.
        masker.addHandler(new CustomExampleMaskingHandler()); // 사용자 정의 핸들러 사용을 위한 추가 등록
        masker.mask(testObject);

        System.out.println("rrn: " + testObject.rrn); // 970101-#######
        System.out.println("nonMaskedString: " + testObject.nonMaskedString); // 970101-1234567
        System.out.println("driversLicenseNumber: " + testObject.driversLicenseNumber); // 11-11-######-##
        System.out.println("phoneNumber: " + testObject.phoneNumber); // 010-####-5678
        System.out.println("bObject.email: " + testObject.bObject.email); // te**@test.com
        System.out.println("bObject.cardNumber: " + testObject.bObject.cardNumber); // 9409-****-****-****
        System.out.println("bObject.address: " + testObject.bObject.address); // 서울특별시 동대문구 ***** ***
        System.out.println("bObject.customMasked: " + testObject.bObject.customMasked); // *********************
    }

}
