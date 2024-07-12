package gift.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WishDTO {
    @JsonProperty
    private long id;
    @JsonProperty
    private MemberDTO member;
    @JsonProperty
    private ProductDTO product;

    public WishDTO(long id, MemberDTO memberDTO, ProductDTO productDTO) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public static WishDTO getWishDTO(Wish wish){
        return new WishDTO(wish.getId(),
                MemberDTO.getMemberDTO(wish.getMemberId()),
                ProductDTO.getProductDTO(wish.getProductId()));
    }
}
