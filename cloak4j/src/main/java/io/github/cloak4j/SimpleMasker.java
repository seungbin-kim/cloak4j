package io.github.cloak4j;

public class SimpleMasker {

    private SimpleMasker() {
    }

    /**
     * 공백이 아닌 문자열을 받아 {@code startIndex}부터 {@code maskingLength} 길이만큼 마스킹한 결과를 반환합니다.<br/>
     * {@code startIndex} 또는 {@code maskingLength} 값이 0 미만이라면 마스킹되지 않습니다.
     *
     * @param target        마스킹 대상 문자열
     * @param startIndex    마스킹 시작 index
     * @param maskingLength 마스킹 길이
     * @param maskingChar   마스킹 문자
     * @return 마스킹 결과. 인자값이 비정상이라면 {@code target} 값이 그대로 반환됩니다.
     */
    public static String mask(String target, int startIndex, int maskingLength, char maskingChar) {
        if (target == null || target.isBlank()) {
            return target;
        }
        if (startIndex < 0 || maskingLength < 0) {
            return target;
        }

        String s = maskingChar + "";
        int targetLength = target.length();
        if (startIndex >= targetLength) {
            return target;
        }
        if ((long) startIndex + maskingLength > targetLength) {
            return target.substring(0, startIndex) + s.repeat(targetLength - startIndex);
        }

        return target.substring(0, startIndex)
                + s.repeat(maskingLength)
                + target.substring(startIndex + maskingLength);
    }

    /**
     * 공백이 아닌 문자열을 받아 {@code startIndex} 부터 끝까지 마스킹한 결과를 반환합니다.<br/>
     * {@code startIndex} 값이 0 미만이라면 마스킹되지 않습니다.
     *
     * @param target      마스킹 대상 문자열
     * @param startIndex  마스킹 시작 index
     * @param maskingChar 마스킹 문자
     * @return 마스킹 결과. 인자값이 비정상이라면 {@code target} 값이 그대로 반환됩니다.
     */
    public static String mask(String target, int startIndex, char maskingChar) {
        return mask(target, startIndex, Integer.MAX_VALUE, maskingChar);
    }

    /**
     * 공백이 아닌 문자열을 받아 처음부터 {@code endIndex} 까지 마스킹한 결과를 반환합니다.<br/>
     * {@code endIndex} 값이 0 미만이라면 마스킹되지 않습니다.
     *
     * @param target      마스킹 대상 문자열
     * @param maskingChar 마스킹 문자
     * @param endIndex    마스킹 종료 index. 포함되지 않음
     * @return 마스킹 결과. 인자값이 비정상이라면 {@code target} 값이 그대로 반환됩니다.
     */
    public static String mask(String target, char maskingChar, int endIndex) {
        return mask(target, 0, endIndex, maskingChar);
    }

}
