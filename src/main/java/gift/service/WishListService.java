package gift.service;

import gift.domain.Product;
import gift.dto.WishProduct;
import gift.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.constant.Message.ADD_SUCCESS_MSG;
import static gift.constant.Message.DELETE_SUCCESS_MSG;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<Product> getWishList(String email) {
        return wishListRepository.selectWishList(email);
    }

    public String addWishProduct(WishProduct wishProduct) {
        wishListRepository.addWishProduct(wishProduct);
        return ADD_SUCCESS_MSG;
    }

    public String deleteWishProduct(WishProduct wishProduct) {
        wishListRepository.deleteProduct(wishProduct);
        return DELETE_SUCCESS_MSG;
    }
}
