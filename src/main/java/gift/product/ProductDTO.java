package gift.product;

import jakarta.validation.constraints.*;

public class ProductDTO {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎ ()\\[\\]+\\-&/_]*$",
        message="'( ), [ ], +, -, &, /, _'외의 특수문자는 사용할 수 없습니다.")
    @Pattern(regexp = "^(?!.*카카오).*",
        message="\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
    @Size(max=15, message="공백을 포함하여 15자 이내의 이름을 입력해주세요.")
    @NotBlank(message="이름을 입력하지 않았습니다.")
    private String name;

    @Min(value=0, message="유효한 값을 입력해주세요")
    @NotNull(message="가격을 입력하지 않았습니다.")
    @Digits(integer=Integer.MAX_VALUE, fraction=0, message="정수만 입력 가능합니다.")
    private Integer price;

    @NotBlank(message="이미지 링크를 입력하지 않았습니다.")
    private String imageUrl;

    public ProductDTO() {
        this.id = null;
        this.name = null;
        this.price = null;
        this.imageUrl = null;
    }

    public ProductDTO(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }

    public static ProductDTO fromProduct(Product product) {
        return new ProductDTO(product.id(), product.name(), product.price(), product.imageUrl());
    }
}
