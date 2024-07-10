package gift.model.wish;

import gift.model.product.Product;

public record WishResponse(Long wishId, Long productId, String productName, int price, String imageUrl, int count) {

    public static WishResponse from(Wish wish, Product product) {
        return new WishResponse(wish.getId(), product.getId(), product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            wish.getCount());
    }
}
