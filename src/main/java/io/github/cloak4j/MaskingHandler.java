package io.github.cloak4j;

public interface MaskingHandler {

    String mask(String in, char maskChar);

    boolean supports(String in);

}
