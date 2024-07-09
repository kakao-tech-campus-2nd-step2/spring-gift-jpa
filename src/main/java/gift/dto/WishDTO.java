package gift.dto;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;

public class WishDTO {

    private Member member;
    private Product product;

    public WishDTO() {

    }

    public WishDTO(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }
}
