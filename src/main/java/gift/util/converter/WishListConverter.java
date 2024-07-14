package gift.util.converter;

import gift.dto.WishDTO;
import gift.dto.WishListDTO;
import gift.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class WishListConverter {
    public static WishListDTO convertToWishListDTO(Page<Wish> wishPage) {
        List<WishDTO> wishDTOList = wishPage.getContent().stream()
                .map(wish -> new WishDTO(wish.getId(), wish.getProduct(), wish.getQuantity()))
                .collect(Collectors.toList());

        Page<WishDTO> wishDTOPage = new PageImpl<>(wishDTOList, wishPage.getPageable(), wishPage.getTotalElements());

        return new WishListDTO(wishDTOPage);
    }
}