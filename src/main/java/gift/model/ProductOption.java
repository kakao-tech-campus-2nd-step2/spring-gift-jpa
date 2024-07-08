package gift.model;

public class ProductOption {

    private Long id;
    private Long productId;
    private String name;
    private Integer additionalPrice;

    public ProductOption(Long productId, String name, Integer additionalPrice) {
        this.productId = productId;
        this.name = name;
        this.additionalPrice = additionalPrice;
    }

    public ProductOption(Long id, Long productId, String name, Integer additionalPrice) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.additionalPrice = additionalPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getAdditionalPrice() {
        return additionalPrice;
    }

    public void updateOptionInfo(String name, Integer additionalPrice) {
        this.name = name;
        this.additionalPrice = additionalPrice;
    }
}
