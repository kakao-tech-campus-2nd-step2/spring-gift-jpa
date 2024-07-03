package gift.main.handler;

import gift.main.dto.ProductDto;
import gift.main.dto.ProductRequest;

public class ProductValidator {

    public static ProductDto isValidProductDto(ProductRequest productRequest) {
        if (productRequest.name() == null || productRequest.name().length() > 15 ) {
            throw new IllegalArgumentException("상품명은 공백 포함 1~15자사이여야합니다.");
        }

        if (!productRequest.name().matches("^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_가-힣]+$")){
            throw new IllegalArgumentException("상품 이름에는 허용되는 특수 문자만 사용할 수 있습니다. 허용되는 특수 문자는 ( ), [ ], +, -, &, /, _ 입니다.");
        }

        if (productRequest.name().contains("카카오")) {
            throw new IllegalArgumentException("카카오라는 이름을 사용시에 관리자에게 문의하세요");
        }
        String validName = productRequest.name();

        if(productRequest.price()>=0){
            throw new IllegalArgumentException("가격은 음수일수 없습니다");
        }
        int validPrice = productRequest.price();

        String validimageUrl = productRequest.imageUrl();

        ProductDto productDto = new ProductDto(validName, validPrice, validimageUrl);
        return productDto;
    }
}
