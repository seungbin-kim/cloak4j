package examples.object_masking.auto_masking;

import io.github.cloak4j.annotation.AutoMasking;
import io.github.cloak4j.annotation.IgnoreMasking;

@AutoMasking(maskingChar = '#')
class A {

    String rrn;

    @IgnoreMasking
    String nonMaskedString;

    String driversLicenseNumber;

    String phoneNumber;

    B bObject;

    @AutoMasking
    static class B {

        String email;

        String cardNumber;

        String address;

        String customMasked;

    }

}
