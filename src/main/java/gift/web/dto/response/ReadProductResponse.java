package gift.web.dto.response;

import gift.domain.Product;
import java.net.URL;

public class ReadProductResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final URL imageUrl;

    private ReadProductResponse(Long id, String name, Integer price, URL imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ReadProductResponse fromEntity(Product product) {
        return new ReadProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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

    public URL getImageUrl() {
        return imageUrl;
    }

}
