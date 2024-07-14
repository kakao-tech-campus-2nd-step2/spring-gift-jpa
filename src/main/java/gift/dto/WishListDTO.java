package gift.dto;

import org.springframework.data.domain.Page;

public record WishListDTO(Page<WishDTO> wishList) {

}