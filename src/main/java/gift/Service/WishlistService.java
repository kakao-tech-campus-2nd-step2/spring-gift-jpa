package gift.Service;

import gift.Entity.Wishlist;
import gift.Mapper.Mapper;
import gift.Model.WishlistDto;
import gift.Repository.WishlistJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistJpaRepository wishListJpaRepository;
    private final Mapper mapper;

    @Autowired
    public WishlistService(WishlistJpaRepository wishlistJpaRepository, Mapper mapper) {
        this.wishListJpaRepository = wishlistJpaRepository;
        this.mapper = mapper;
    }

    public List<Wishlist> getWishlist(long userId) {
        return wishListJpaRepository.findByIdUserId(userId);
    }

    public void addWishlistItem(WishlistDto wishlistDto) {
        wishlistDto.setPrice(wishlistDto.getPrice() * wishlistDto.getCount());
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        wishListJpaRepository.save(wishlist);
    }

    public void removeWishlistItem(WishlistDto wishListItem, Wishlist wishlistOptional) {
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
