package gift.entity;

import gift.exception.BusinessException;
import gift.exception.ErrorCode;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductName {

    private static final int MAX_NAME_LENGTH = 15;
    private static final String DISALLOWED_NAME_SUBSTRING = "카카오";
    private static final String NAME_PATTERN = "^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]+\\-&/_]*$";
    private static final String NULL_NAME_ERROR_MESSAGE = "상품 이름은 null 일 수 없습니다.";
    private static final String MAX_LENGTH_ERROR_MESSAGE = "상품 이름은 15자 이하여야합니다. (공백포함)";
    private static final String PATTERN_ERROR_MESSAGE = "( ), [ ], +, -, &, /, _ 외 특수문자는 사용 불가능합니다.";

    @NotNull(message = NULL_NAME_ERROR_MESSAGE)
    @Size(max = MAX_NAME_LENGTH, message = MAX_LENGTH_ERROR_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = PATTERN_ERROR_MESSAGE)
    private final String value;

    public ProductName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_NAME_SIZE);
        }
        if (value.contains(DISALLOWED_NAME_SUBSTRING)) {
            throw new BusinessException(ErrorCode.KAKAO_NAME_NOT_ALLOWED);
        }
        if (value.length() > MAX_NAME_LENGTH) {
            throw new BusinessException(ErrorCode.INVALID_NAME_SIZE);
        }
        if (!value.matches(NAME_PATTERN)) {
            throw new BusinessException(ErrorCode.INVALID_NAME_PATTERN);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
