package examples.object_masking.custom_annotation;

import io.github.cloak4j.MaskingHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
라이브러리의 MaskingHandler 인터페이스를 구현한 사용자 정의 핸들러 입니다.
 */
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

    /*
    @AutoMasking 동작시 호출됩니다.
     */
    @Override
    public boolean supports(String in) {
        if (in == null) {
            return false;
        }

        return CUSTOM_PATTERN.matcher(in).matches();
    }

}
