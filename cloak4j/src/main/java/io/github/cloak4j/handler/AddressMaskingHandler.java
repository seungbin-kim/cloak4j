package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AddressMaskingHandler implements MaskingHandler {

    private final Pattern ADDRESS_PATTERN;

    public AddressMaskingHandler() {
        this.ADDRESS_PATTERN =
                Pattern.compile("^(?<nonMasking>\\S*[시도]\\s(?:\\S*[시군구])?\\s?)(?<masking>.*)");
    }

    @Override
    public String mask(String in, char maskingChar) {

        if (in == null || in.isBlank()) {
            return in;
        }

        Matcher matcher = this.ADDRESS_PATTERN.matcher(in);
        if (!matcher.matches()) {
            return in;
        }

        return matcher.group("nonMasking")
                + matcher.group("masking").replaceAll("\\S", maskingChar + "");
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }
        return ADDRESS_PATTERN.matcher(in).matches();
    }

}
