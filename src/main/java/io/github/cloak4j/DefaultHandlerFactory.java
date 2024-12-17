package io.github.cloak4j;

import io.github.cloak4j.handler.AddressMaskingHandler;
import io.github.cloak4j.handler.CardNumberMaskingHandler;
import io.github.cloak4j.handler.DriversLicenseNumberMaskingHandler;
import io.github.cloak4j.handler.EmailMaskingHandler;
import io.github.cloak4j.handler.PhoneNumberMaskingHandler;
import io.github.cloak4j.handler.RRNMaskingHandler;

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
