package gift.product;

import jakarta.validation.constraints.*;

public class ProductDTO {

    long id;
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎ ()\\[\\]+\\-&/_]*$",
        message = "'( ), [ ], +, -, &, /, _'외의 특수문자는 사용할 수 없습니다.")
    @Pattern(regexp = "^(?!.*카카오).*",
        message = "\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
    @Size(max = 15, message = "공백을 포함하여 15자 이내의 이름을 입력해주세요.")
    @NotBlank(message = "이름을 입력하지 않았습니다.")
    String name;
    @Min(value = 0, message = "유효한 값을 입력해주세요")
    @NotNull(message = "가격을 입력하지 않았습니다.")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "정수만 입력 가능합니다.")
    int price;
    @NotBlank(message = "이미지 링크를 입력하지 않았습니다.")
    String imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }

    public static ProductDTO fromProduct(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }
}
