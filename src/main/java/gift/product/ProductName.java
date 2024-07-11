package gift.product;

import gift.exception.KakaoProductException;

public class ProductName {
    private String value;

    public ProductName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // 비즈니스 로직: 상품명에 '카카오'가 포함되었는지 검증
    public void validate() {
        if (value != null && value.contains("카카오")) {
            throw new KakaoProductException("상품명에 '카카오'가 포함된 경우 담당 MD에게 문의하세요.");
        }
    }
}