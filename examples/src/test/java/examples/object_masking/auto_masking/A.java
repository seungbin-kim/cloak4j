package examples.object_masking.auto_masking;

import io.github.cloak4j.annotation.AutoMasking;
import io.github.cloak4j.annotation.IgnoreMasking;

@AutoMasking(maskingChar = '#') // 자동 마스킹시 사용할 마스킹 문자 지정 가능
class A {

    String rrn;

    @IgnoreMasking
    String nonMaskedString;

    String driversLicenseNumber;

    String phoneNumber;

    B bObject;

    @AutoMasking // 자동 마스킹 문자는 기본적으로 '*' 입니다.
    static class B {
        /*
        자동 마스킹시 수동 마스킹처럼 핸들러를 직접 지정하면 지정한대로 처리됩니다.
        @FieldMasking(handler = EmailMaskingHandler.class, maskingChar = '?')
         */
        String email;

        String cardNumber;

        String address;

        /*
        사용자 정의 핸들러인 CustomExampleMaskingHandler 작성,
        ObjectMasker 생성후 addHandler(new CustomExampleMaskingHandler()) 으로 사용자 정의 핸들러를 추가 등록하였기 때문에
        @AutoMasking 사용시 등록된 핸들러의 supports() 의 값이 true -> 자동 마스킹 됩니다.
         */
        String customMasked;

    }

}
