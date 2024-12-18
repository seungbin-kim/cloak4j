package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CardNumberMaskingHandler implements MaskingHandler {

    private final Pattern PHONE_NUMBER_PATTERN;

    public CardNumberMaskingHandler() {
        this.PHONE_NUMBER_PATTERN =
                Pattern.compile("^(?<nonMasking>\\d{4}-?)(?<masking>\\d{6}-?\\d{5}|\\d{4}-?\\d{4}-?\\d{3,4})$");
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

        return matcher.group("nonMasking")
                + matcher.group("masking").replaceAll("\\d", maskingChar + "");
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }
        return PHONE_NUMBER_PATTERN.matcher(in).matches();
    }

}
