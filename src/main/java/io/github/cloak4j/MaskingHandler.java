package io.github.cloak4j;

public interface MaskingHandler {

    /**
     * 마스킹 처리 메서드
     *
     * @param in       처리 대상 문자열 입력
     * @param maskChar 마스킹 문자
     * @return 마스킹 문자로 마스킹 된 문자열
     */
    String mask(String in, char maskChar);

    /**
     * {@link io.github.cloak4j.annotation.AutoMasking} 사용시 마스킹 처리가 가능한지 확인하기 위해 호출되는 메서드
     *
     * @param in 마스킹 대상 문자열
     * @return 처리 가능하다면 true, 아니라면 false
     */
    boolean supports(String in);

}
