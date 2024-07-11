package gift.converter;

import gift.dto.ProductDTO;
import gift.dto.UserDTO;
import gift.dto.WishListDTO;
import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import java.util.List;
import java.util.stream.Collectors;

public class WishListConverter {

    public static WishListDTO convertToDTO(WishList wishList) {
        return new WishListDTO(
            wishList.getId(),
            UserConverter.convertToDTO(wishList.getUser()),
            ProductConverter.convertToDTO(wishList.getProduct())
        );
    }

    public static WishList convertToEntity(WishListDTO wishListDTO) {
        return new WishList(
            wishListDTO.getId(),
            UserConverter.convertToEntity(wishListDTO.getUser()),
            ProductConverter.convertToEntity(wishListDTO.getProduct())
        );
    }
}