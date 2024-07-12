package gift.Service;

import gift.Entity.Wishlist;
import gift.Model.WishListItem;
import gift.Repository.WishListJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListJpaRepository wishListJpaRepository;

    @Autowired
    public WishListService(WishListJpaRepository wishListJpaRepository) {
        this.wishListJpaRepository = wishListJpaRepository;
    }

    public List<Wishlist> getWishlist(long userId) {
        return wishListJpaRepository.findByIdUserId(userId);
    }

    public void addWishlistItem(WishListItem wishlistItem) {
        wishlistItem.setPrice(wishlistItem.getPrice() * wishlistItem.getCount());
        Wishlist wishlist = Wishlist.createWishlist(wishlistItem);
        wishListJpaRepository.save(wishlist);
    }

    public void removeWishlistItem(WishListItem wishListItem, Wishlist wishlistOptional) {
        int newCount = wishListItem.getCount() - wishListItem.getQuantity();

        if (newCount > 0) {
            wishlistOptional.setCount(newCount);
            wishlistOptional.setPrice(wishlistOptional.getPrice() / wishListItem.getCount() * newCount);
            wishListJpaRepository.save(wishlistOptional);
            return;
        }

        wishListJpaRepository.delete(wishlistOptional);
    }

}
