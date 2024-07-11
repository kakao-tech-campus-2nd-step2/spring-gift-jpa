package gift.service;

import gift.dto.WishlistDTO;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository){
        this.wishlistRepository = wishlistRepository;
    }

    public void addWishlist(WishlistDTO wishlistDTO){
        Wishlist wishlist = new Wishlist(
                wishlistDTO.getUserId(),
                wishlistDTO.getProductId()
        );

        wishlistRepository.save(wishlist);
    }

    public List<WishlistDTO> loadWishlist(String userId){
        List<WishlistDTO> list = new ArrayList<>();

        List<Wishlist> wishlists = wishlistRepository.findByUserId(userId);
        for (Wishlist wishlist : wishlists) {
            WishlistDTO wishlistDTO = new WishlistDTO(
                    wishlist.getUserId(),
                    wishlist.getProductId()
            );
            list.add(wishlistDTO);
        }

        return list;
    }

    public void deleteWishlist(String userId, Long productId){
        wishlistRepository.delete(userId, productId);
    }
}