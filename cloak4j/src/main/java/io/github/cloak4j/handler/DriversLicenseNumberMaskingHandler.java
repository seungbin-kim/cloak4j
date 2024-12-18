package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriversLicenseNumberMaskingHandler implements MaskingHandler {

    private final Pattern DRIVERS_LICENSE_NUMBER_PATTERN;

    public DriversLicenseNumberMaskingHandler() {
        this.DRIVERS_LICENSE_NUMBER_PATTERN =
                Pattern.compile("^(?<nonMasking>\\d{2}-\\d{2}-)(?<masking>\\d{6}-\\d{2})$");
    }

    @Override
    public String mask(String in, char maskingChar) {

        if (in == null || in.isBlank()) {
            return in;
        }

        Matcher matcher = this.DRIVERS_LICENSE_NUMBER_PATTERN.matcher(in);
        if (!matcher.matches()) {
            return in;
        }

        return matcher.group("nonMasking")
                + matcher.group("masking").replaceAll("\\d", maskingChar + "");
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }
        return DRIVERS_LICENSE_NUMBER_PATTERN.matcher(in).matches();
    }

}
