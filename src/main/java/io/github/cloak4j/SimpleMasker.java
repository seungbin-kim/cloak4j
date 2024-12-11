package io.github.cloak4j;

public class SimpleMasker {

    public static String mask(String target, int startIndex, int maskingLength, char maskingChar) {
        if (target == null || target.isBlank()) {
            return target;
        }
        if (startIndex < 0 || maskingLength < 0) {
            return target;
        }

        String s = maskingChar + "";
        int targetLength = target.length();
        if ((long) startIndex + maskingLength > targetLength) {
            return target.substring(0, startIndex) + s.repeat(targetLength - startIndex);
        }

        return target.substring(0, startIndex)
                + s.repeat(targetLength - startIndex)
                + target.substring(startIndex + maskingLength);
    }


    public static String mask(String target, int startIndex, char maskingChar) {
        return mask(target, startIndex, Integer.MAX_VALUE, maskingChar);
    }


    public static String mask(String target, char maskingChar, int endIndex) {
        return mask(target, 0, endIndex, maskingChar);
    }

}
