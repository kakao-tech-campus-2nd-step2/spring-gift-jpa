package gift.main.handler;

import gift.main.entity.Product;

import java.util.Map;

public class MapToProductTransformer {
    public static Product convertMapToProduct(Map<String, Object> map) {
        Long id = Long.valueOf((int)map.get("id"));
        String name = (String) map.get("name");
        int price = (int) map.get("price");
        String imageUrl = (String) map.get("imageUrl");

        return new Product(id, name, price, imageUrl);
    }

}