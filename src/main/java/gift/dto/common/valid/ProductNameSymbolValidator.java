package gift.dto.common.valid;

/*
쓰지 않는 클래스
public class ProductNameSymbolValidator implements ConstraintValidator<ValidProductNameSymbol, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {

        Pattern symbol = Pattern.compile("^[\\w가-힣ㄱ-ㅎㅏ-ㅣ()\\[\\]+\\-&/_]*$");
        Matcher symbolMatcher = symbol.matcher(name);

        return symbolMatcher.matches();
    }
}


 */