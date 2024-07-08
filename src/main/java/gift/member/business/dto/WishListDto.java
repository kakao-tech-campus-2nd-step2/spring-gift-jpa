package gift.member.business.dto;

import gift.product.persistence.entity.Product;

public record WishListDto(
    Long id,
    Long productId,
    String productName,
    Integer price,
    String imageUrl,
    Integer count
) {

    public static WishListDto of(Long id, Product product, Integer count) {
        return new WishListDto(id, product.getId(), product.getName(), product.getPrice(),
            product.getUrl(), count);
    }

}
