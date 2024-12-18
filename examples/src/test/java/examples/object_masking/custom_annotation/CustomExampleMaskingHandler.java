package examples.object_masking.custom_annotation;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomExampleMaskingHandler implements MaskingHandler {

    private final Pattern CUSTOM_PATTERN;

    public CustomExampleMaskingHandler() {
        this.CUSTOM_PATTERN = Pattern.compile("(?<masking>.*)");
    }

    @Override
    public String mask(String in, char maskChar) {
        if (in == null) {
            return null;
        }

        Matcher matcher = CUSTOM_PATTERN.matcher(in);
        if (matcher.matches()) {
            return (maskChar + "").repeat(matcher.group("masking").length());
        }

        return in;
    }

    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }

        return CUSTOM_PATTERN.matcher(in).matches();
    }

}
