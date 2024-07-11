package gift.dto;

import gift.domain.Member;
import gift.domain.Product;

public class WishResponseDto {
    private Long id;
    private Member member;
    private Product product;
    private int quantity;

    public WishResponseDto(Long id, Member member, Product product, int quantity) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }
}