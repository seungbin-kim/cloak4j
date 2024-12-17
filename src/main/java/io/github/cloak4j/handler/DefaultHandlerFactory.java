package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.Set;

public class DefaultHandlerFactory {

    public static Set<MaskingHandler> getDefaultHandlerSet() {
        return Set.of(
                new RRNMaskingHandler(),
                new PhoneNumberMaskingHandler(),
                new EmailMaskingHandler(),
                new DriversLicenseNumberMaskingHandler(),
                new CardNumberMaskingHandler(),
                new AddressMaskingHandler()
        );
    }

}
