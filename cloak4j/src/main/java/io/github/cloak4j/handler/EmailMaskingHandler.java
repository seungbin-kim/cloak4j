package io.github.cloak4j.handler;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailMaskingHandler implements MaskingHandler {

    private final Pattern EMAIL_PATTERN;

    public EmailMaskingHandler() {
        this.EMAIL_PATTERN =
                Pattern.compile("^(?<local>([\\w!-_.]+))@(?<domain>([\\w!-_.])*\\.\\w{2,3})$");
    }

    @Override
    public String mask(String in, char maskingChar) {

        if (in == null || in.isBlank()) {
            return in;
        }

        Matcher matcher = this.EMAIL_PATTERN.matcher(in);
        if (!matcher.matches()) {
            return in;
        }

        String local = matcher.group("local");
        String domain = matcher.group("domain");
        int localLength = local.length();
        if (localLength == 1) {
            return maskingChar + "@" + domain;
        }
        if (localLength == 2) {
            return local.substring(0, 1) + maskingChar + "@" + domain;
        }
        return local.substring(0, 2) + (maskingChar + "").repeat(localLength - 2) + "@" + domain;
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(in).matches();
    }

}
