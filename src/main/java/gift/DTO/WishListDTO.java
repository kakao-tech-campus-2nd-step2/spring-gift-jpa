package gift.DTO;

import gift.auth.DTO.MemberDTO;
import jakarta.validation.constraints.NotNull;

public record WishListDTO(
    @NotNull
    Long id,

    @NotNull
    Long productId,
    @NotNull
    Long userId,

    ProductDTO productDTO,

    MemberDTO memberDTO
) {

    public WishListDTO(ProductDTO productDTO, MemberDTO memberDTO) {
        this(-1, productDTO, memberDTO);
    }

    public WishListDTO(long id, ProductDTO productDTO, MemberDTO memberDTO) {
        this(id, productDTO.id(), memberDTO.getId(), productDTO, memberDTO);
    }
}