package io.github.cloak4j;

import io.github.cloak4j.handler.AddressMaskingHandler;
import io.github.cloak4j.handler.CardNumberMaskingHandler;
import io.github.cloak4j.handler.DriversLicenseNumberMaskingHandler;
import io.github.cloak4j.handler.EmailMaskingHandler;
import io.github.cloak4j.handler.PhoneNumberMaskingHandler;
import io.github.cloak4j.handler.RRNMaskingHandler;

import java.util.Set;

public class DefaultHandlerFactory {

    /**
     * 라이브러리가 기본으로 제공하는 마스킹 핸들러들을 생성합니다.
     *
     * @return 기본제공 핸들러 Set
     */
    public static Set<MaskingHandler> generateDefaultHandlerSet() {
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
