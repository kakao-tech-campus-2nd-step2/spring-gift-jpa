package gift.dto.common.valid;

/*
쓰지 않는 클래스
public class ProductNameKakaoValidator implements ConstraintValidator<ValidProductNameKakao, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Pattern kakao = Pattern.compile("^(?!.*카카오).*$");
        Matcher kakaoMatcher = kakao.matcher(name);

        return kakaoMatcher.matches();
    }
}

 */