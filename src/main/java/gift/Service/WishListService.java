package gift.Service;

import gift.Model.WishListItem;
import gift.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishListItem> getWishlist(int userId) {
        return wishListRepository.getWishlist(userId);
    }

    public void addWishlistItem(WishListItem wishlistItem) {
        wishListRepository.addWishlistItem(wishlistItem);
    }

    public void removeWishlistItem(WishListItem wishListItem) {
        //값만 줄일건지
        if(wishListItem.getCount() - wishListItem.getQuantity() > 0) {
            wishListRepository.reduceWishlistItem(wishListItem);
            return;
        }
        wishListRepository.removeWishlistItem(wishListItem);
    }

}
