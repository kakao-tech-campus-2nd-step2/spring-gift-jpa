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
        UserDTO userDTO = UserConverter.convertToDTO(wishList.getUser());
        List<ProductDTO> productDTOs = wishList.getProducts().stream()
            .map(ProductConverter::convertToDTO)
            .collect(Collectors.toList());
        return new WishListDTO(wishList.getId(), userDTO, productDTOs);
    }

    public static WishList convertToEntity(WishListDTO wishListDTO) {
        User user = UserConverter.convertToEntity(wishListDTO.getUser());
        List<Product> products = wishListDTO.getProducts().stream()
            .map(ProductConverter::convertToEntity)
            .collect(Collectors.toList());
        return new WishList(wishListDTO.getId(), user, products);
    }
}