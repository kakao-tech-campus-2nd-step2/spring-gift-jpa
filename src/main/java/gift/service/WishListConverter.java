package gift.service;

import gift.dto.WishListDTO;
import gift.entity.Wish;

import org.springframework.data.domain.Page;

public class WishListConverter {
    public static WishListDTO convertToDTO(Page<Wish> wishPage) {
        for(Wish wish : wishPage) {
            wish.setMember(null); // 개인정보 보호
        }
        return new WishListDTO(wishPage);
    }
}
