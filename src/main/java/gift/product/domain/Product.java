package gift.product.domain;

import gift.exception.type.KakaoInNameException;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void update(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void validateKakaoInName() {
        if (name.contains("카카오")) {
            throw new KakaoInNameException("카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }
    }
}
