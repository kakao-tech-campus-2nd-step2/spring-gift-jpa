package gift.dto.request;

import gift.domain.Product;
import gift.exception.InvalidProductDataException;
import gift.validation.KakaoApproval;
import jakarta.validation.constraints.*;


public class ProductRequest {

    @NotBlank(message = "상품 이름은 필수 항목입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ()\\[\\]+\\-&/_ ]*$",
            message = "상품 이름에 허용되지 않는 특수 문자가 포함되어 있습니다."
    )
    @KakaoApproval
    private String name;

    @NotNull(message = "가격을 입력하세요")
    @Positive(message = "가격은 양의 정수여야 합니다.")
    private long price;

    private String imageUrl;

    public ProductRequest(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductRequest() {}

    public String getName(){
        return name;
    }

    private boolean isApprovedByMD() {
        return false;
    }

    public long getPrice(){
        return price;
    }
    public String getImageUrl(){
        return imageUrl;
    }

    public static ProductRequest entityToRequest(Product product){
        return new ProductRequest(product.getName(), product.getPrice(), product.getImageUrl());
    }
}
