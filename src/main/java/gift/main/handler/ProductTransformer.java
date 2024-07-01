package gift.main.handler;

import gift.main.dto.ProductRequest;
import gift.main.entity.Product;

public class ProductTransformer {
    public static Product convertToProduct(long id, ProductRequest productRequest) {
        String name = productRequest.name();
        int price = productRequest.price();
        String imageUrl = productRequest.imageUrl();

        return new Product(id, name, price, imageUrl);
    }

}

