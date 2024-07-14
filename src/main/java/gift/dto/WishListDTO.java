package gift.dto;

import gift.entity.Wish;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public final class WishListDTO {

    private final Page<WishDTO> wishList;

    public WishListDTO(Page<WishDTO> wishList) {
        this.wishList = wishList;
    }

    public Page<WishDTO> getWishList() {
        return wishList;
    }

    public static WishListDTO convertToWishListDTO(Page<Wish> wishPage) {
        List<WishDTO> wishDTOList = wishPage.getContent().stream().map(WishDTO::convertToWishDTO).toList();
        Page<WishDTO> wishDTOPage = new PageImpl<>(wishDTOList, wishPage.getPageable(), wishPage.getTotalElements());
        return new WishListDTO(wishDTOPage);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (WishListDTO) obj;
        return Objects.equals(this.wishList, that.wishList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishList);
    }

}