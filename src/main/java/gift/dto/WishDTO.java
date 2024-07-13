package gift.dto;

import gift.entity.Product;

public class WishDTO {
    public static class wishListProduct {
        String name;
        int price;
        String imageUrl;

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public wishListProduct(String name, int price, String imageUrl) {
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public wishListProduct(Product product) {
            this.name = product.getName();
            this.price = product.getPrice();
            this.imageUrl = product.getImageUrl();
        }
    }
}
