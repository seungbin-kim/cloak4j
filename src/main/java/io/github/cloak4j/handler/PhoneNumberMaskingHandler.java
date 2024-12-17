package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberMaskingHandler implements MaskingHandler {

    private final Pattern PHONE_NUMBER_PATTERN;

    public PhoneNumberMaskingHandler() {
        this.PHONE_NUMBER_PATTERN =
                Pattern.compile("^(?<head>(010|070|02|0[3-9][0-9])-?)(?<masking>[0-9]{3,4})(?<tail>-?[0-9]{4})$");
    }

    @Override
    public String mask(String in, char maskingChar) {

        if (in == null || in.isBlank()) {
            return in;
        }

        Matcher matcher = this.PHONE_NUMBER_PATTERN.matcher(in);
        if (!matcher.matches()) {
            return in;
        }

        return matcher.group("head")
                + (maskingChar + "").repeat(matcher.group("masking").length())
                + matcher.group("tail");
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }
        return PHONE_NUMBER_PATTERN.matcher(in).matches();
    }

}
