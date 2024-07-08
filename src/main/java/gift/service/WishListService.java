package gift.service;

import gift.model.WishList;
import gift.model.WishListDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishList(WishListDTO wishListDTO) {
        wishListRepository.createWishList(wishListDTO);
    }

    public List<WishListDTO> getAllWishList() {
        List<WishList> wishlists = wishListRepository.getAllWishList();
        return wishlists.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<WishListDTO> getWishListById(long id) {
        List<WishList> wishlists = wishListRepository.getWishListById(id);
        return wishlists.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public void updateWishListQuantity(WishListDTO wishListDTO) {
        wishListRepository.updateWishListQuantity(wishListDTO);
    }

    public void deleteWishList(String memberId, String productName) {
        wishListRepository.deleteWishList(memberId, productName);
    }

    private WishListDTO convertToDTO(WishList wishList) {
        return new WishListDTO(wishList.getEmail(), wishList.getMemberId(), wishList.getproductName(),
            wishList.getQuantity());
    }
}
