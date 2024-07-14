package gift.util.converter;

import gift.dto.WishDTO;
import gift.dto.WishListDTO;
import gift.entity.Wish;
import java.util.ArrayList;
import java.util.List;

public class WishListConverter {
    public static WishListDTO convertToWishListDTO(List<Wish> wishList) {
        List<Wish> wishListCopied = new ArrayList<>(wishList);
        List<WishDTO> wishDTOList = new ArrayList<>(wishListCopied.size());
        for (Wish wish : wishListCopied) {
            wishDTOList.add(new WishDTO(wish.getId(), wish.getProduct(), wish.getQuantity()));
        }
        return new WishListDTO(wishDTOList);
    }
}
