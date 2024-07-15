package gift.util;

import gift.exception.CustomException;

import java.util.regex.Pattern;

import static gift.exception.ErrorCode.KAKAO_CONTAIN_ERROR;
import static gift.exception.ErrorCode.SPECIAL_CHAR_ERROR;

public class ProductNameValidationUtil {

    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9\\s()\\[\\]+\\-&/_가-힣]");

    public static void isValidProductName(String productName) {
        if (containsSpecialCharacters(productName))
            throw new CustomException(SPECIAL_CHAR_ERROR);
        if (containsKAKAO(productName))
            throw new CustomException(KAKAO_CONTAIN_ERROR);
    }

    private static boolean containsSpecialCharacters(String productName) {
        return !productName.isEmpty() & SPECIAL_CHAR_PATTERN.matcher(productName).find();
    }

    private static boolean containsKAKAO(String productName) {
        return !productName.isEmpty() & productName.contains("카카오");
    }
}