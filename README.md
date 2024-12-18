# cloak4j

<p align="center">
  <img src="docs/images/logo.png" alt="logo">
</p>

cloak4j는 문자열을 마스킹 처리해 주는 자바 라이브러리 입니다.

마스킹 메서드로 문자열을 간단히 마스킹하거나 객체가 가진 필드 값들을 손쉽게 마스킹할 수 있습니다.

기본적인 마스킹 처리기 구현체를 제공하며(1.0.0 버전 기준 6개), 어노테이션으로 객체 필드의 수동 및 자동 마스킹 처리가 가능합니다.

| Masking Handler 이름                 | 설명                                            | 비고                                                           |
|------------------------------------|-----------------------------------------------|--------------------------------------------------------------|
| AddressMaskingHandler              | 시/군/구 하위 내용을 마스킹 처리합니다.                       | 마스킹 처리되지 않는 **도시와 시/군/구** 정보는 이어지지 않고 **공백으로 구분**되어 있어야 합니다. |
| CardNumberMaskingHandler           | 카드번호 앞 4자리를 제외한 나머지를 마스킹 처리합니다.               | **하이픈(-)에 상관없이** 동작합니다.                                      |
| DriversLicenseNumberMaskingHandler | 운전면허 번호 앞 4자리를 제외한 나머지를 마스킹 처리합니다.            | **하이픈(-)이 존재**해야 합니다.                                        |
| EmailMaskingHandler                | 이메일 주소 **로컬 부분**중 앞 2자리를 제외한 나머지를 마스킹 처리합니다.  | 도메인 부분은 마스킹되지 않으며, 로컬 부분이 2자리 이하라면 뒤 1자리만 마스킹 처리합니다.         |
| PhoneNumberMaskingHandler          | 010, 070, 지역번호가 존재하는 전화번호의 중간 4자리를 마스킹 처리합니다. | **하이픈(-)에 상관없이** 동작합니다.                                      |
| RRNMaskingHandler                  | 주민등록번호 뒤 7자리를 마스킹 처리합니다.                      | **하이픈(-)에 상관없이** 동작합니다.                                      |

제공되는 기본 처리기 외에 사용자 정의 마스킹 처리기를 구현할 수 있으며, 사용자 정의 어노테이션으로 더욱 간단한 사용이 가능합니다.

자세한 사용 예제들은 [examples 프로젝트](examples/src/test/java/examples)의 *Example.java 패키지에 함께 존재하는 코드들을 참고해 주세요.

# 의존성 가져오기

JitPack 저장소와 의존성을 추가합니다.

## gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'com.github.seungbin-kim:cloak4j:1.0.0'
}
```

## maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.seungbin-kim</groupId>
    <artifactId>cloak4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

# 사용방법

examples 프로젝트에 예제 코드가 주석과 함께 존재합니다.

[간단한 문자열 마스킹](examples/src/test/java/examples/simple_masking/SimpleMaskingExample.java)

[객체 필드값 수동마스킹](examples/src/test/java/examples/object_masking/manual_masking/ManualMaskingExample.java)

[객체 필드값 자동마스킹 및 사용자 정의 마스킹 처리기 활용](examples/src/test/java/examples/object_masking/auto_masking/AutoMaskingExample.java)

[사용자 정의 어노테이션 활용](examples/src/test/java/examples/object_masking/custom_annotation/CustomAnnotationExample.java)

[마스킹 처리기 사용](examples/src/test/java/examples/using_only_handler/UsingOnlyHandlerExample.java)
