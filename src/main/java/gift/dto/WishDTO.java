package gift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gift.model.Wish;

public class WishDTO {
    @JsonProperty
    private long id;
    @JsonProperty
    private MemberDTO member;
    @JsonProperty
    private ProductDTO product;

    public WishDTO(long id, MemberDTO memberDTO, ProductDTO productDTO) {
        this.id = id;
        this.member = memberDTO;
        this.product = productDTO;
    }

    public static WishDTO getWishDTO(Wish wish) {
        return new WishDTO(
                wish.getId(),
                MemberDTO.getMemberDTO(wish.getMember()),  // MemberDTO로 변환
                ProductDTO.getProductDTO(wish.getProduct())  // ProductDTO로 변환
        );
    }

    public long getId() {
        return id;
    }

    public MemberDTO getMember() {
        return member;
    }

    public ProductDTO getProduct() {
        return product;
    }
}
