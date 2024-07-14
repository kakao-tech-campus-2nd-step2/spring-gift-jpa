package gift.model.wish;

public record WishResponse(Long wishId, Long productId, String productName, int price,
                           String imageUrl, int count) {

    public static WishResponse from(Wish wish) {
        return new WishResponse(wish.getId(), wish.getProduct().getId(),
            wish.getProduct().getName(),
            wish.getProduct().getPrice(),
            wish.getProduct().getImageUrl(),
            wish.getCount());
    }
}
