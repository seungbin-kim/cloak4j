package examples.object_masking.manual_masking;

import io.github.cloak4j.annotation.FieldMasking;
import io.github.cloak4j.annotation.ManualMasking;
import io.github.cloak4j.handler.AddressMaskingHandler;
import io.github.cloak4j.handler.CardNumberMaskingHandler;
import io.github.cloak4j.handler.DriversLicenseNumberMaskingHandler;
import io.github.cloak4j.handler.EmailMaskingHandler;
import io.github.cloak4j.handler.PhoneNumberMaskingHandler;
import io.github.cloak4j.handler.RRNMaskingHandler;

/*
@FieldMasking 지정하는 핸들러 클래스는 ObjectMasker 생성/사용시 등록되어 있어야 합니다.
 */
@ManualMasking
class A {

    @FieldMasking(handler = RRNMaskingHandler.class)
    String rrn;

    String nonMaskedString;

    @FieldMasking(handler = DriversLicenseNumberMaskingHandler.class, maskingChar = '@')
    String driversLicenseNumber;

    @FieldMasking(handler = PhoneNumberMaskingHandler.class, maskingChar = '$')
    String phoneNumber;

    B bObject;

    @ManualMasking
    static class B {

        @FieldMasking(handler = EmailMaskingHandler.class)
        String email;

        @FieldMasking(handler = CardNumberMaskingHandler.class, maskingChar = '&')
        String cardNumber;

        @FieldMasking(handler = AddressMaskingHandler.class, maskingChar = '#')
        String address;

    }

}
