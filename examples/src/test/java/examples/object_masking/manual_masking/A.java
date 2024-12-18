package examples.object_masking.manual_masking;

import io.github.cloak4j.annotation.FieldMasking;
import io.github.cloak4j.annotation.ManualMasking;
import io.github.cloak4j.handler.*;

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
