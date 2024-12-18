package examples.object_masking.custom_annotation;

import io.github.cloak4j.annotation.ManualMasking;

@ManualMasking
class A {

    @MaskingRRN
    String rrn;

    String nonMaskedString;

    @MaskingDriversLicense
    String driversLicenseNumber;

    @MaskingPhoneNumber
    String phoneNumber;

    B bObject;

    @ManualMasking
    static class B {

        @MaskingEmail
        String email;

        @MaskingCardNumber
        String cardNumber;

        @MaskingAddress
        String address;

//        @FieldMasking(handler = CustomExampleMaskingHandler.class, maskingChar = '?')
        @MaskingAllWithQuestionMark
        String customMasked;

    }

}
