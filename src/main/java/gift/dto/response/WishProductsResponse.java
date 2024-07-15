package gift.dto.response;

import gift.domain.Product;

public class WishProductsResponse {

    private String name;
    private int price;
    private String imageUrl;

    public WishProductsResponse(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
