package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RRNMaskingHandler implements MaskingHandler {

    private final Pattern RRN_PATTERN;

    public RRNMaskingHandler() {
        this.RRN_PATTERN =
                Pattern.compile("^(?<nonMasking>\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])-?)(?<masking>[1-8]\\d{6})$");
    }

    @Override
    public String mask(String in, char maskingChar) {

        if (in == null || in.isBlank()) {
            return in;
        }

        Matcher matcher = this.RRN_PATTERN.matcher(in);
        if (!matcher.matches()) {
            return in;
        }

        return matcher.group("nonMasking")
                + (maskingChar + "").repeat(matcher.group("masking").length());
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }
        return RRN_PATTERN.matcher(in).matches();
    }

}
