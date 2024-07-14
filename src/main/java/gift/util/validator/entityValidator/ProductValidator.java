package gift.util.validator.entityValidator;

import gift.dto.ProductDTO;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.BlankContentException;
import java.util.regex.Pattern;

public class ProductValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "[a-zA-Z0-9ㄱ-ㅎ가-힣()\\[\\]+\\-&/_ ]+"
    );
    private static final Pattern NAME_EXCLUDE_PATTERN = Pattern.compile(
            "^((?!카카오).)*$"
    );

    public static void validateProduct(ProductDTO productDTO) {
        validateName(productDTO.name());
        validatePrice(productDTO.price());
        validateImageUrl(productDTO.imageUrl());
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BlankContentException("상품 이름을 입력해주세요.");
        }
        if (name.length() > 15) {
            throw new BadRequestException("제품명 길이는 1~15자만 가능합니다.");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new BadRequestException("( ), [ ], +, -, &, /, _을 제외한 특수문자는 입력할 수 없습니다.");
        }
        if (!NAME_EXCLUDE_PATTERN.matcher(name).matches()) {
            throw new BadRequestException("카카오가 포함된 문구는 담당 MD와 협의한 후에 사용해주시기 바랍니다.");
        }
    }

    private static void validatePrice(Integer price) {
        if (price == null) {
            throw new BlankContentException("가격을 입력해주세요.");
        }
        if (price < 0) {
            throw new BadRequestException("가격은 0 이상, 2147483647 이하이여야 합니다.");
        }
    }

    private static void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new BlankContentException("이미지 URL을 입력해주세요.");
        }
    }
}
