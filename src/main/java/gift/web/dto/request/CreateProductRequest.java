package gift.web.dto.request;

import gift.domain.Product;
import java.net.URL;

public class CreateProductRequest {

    private final String name;
    private final Integer price;
    private final URL imageUrl;

    public CreateProductRequest(String name, Integer price, URL imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Product toEntity() {
        return new Product.Builder()
            .name(this.name)
            .price(this.price)
            .imageUrl(this.imageUrl)
            .build();
    }
}
