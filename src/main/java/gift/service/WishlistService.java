package gift.service;

import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Product> getWishlistByEmail(String email) {
        List<Wishlist> wishlist = wishlistRepository.findByMemberEmail(email);
        return wishlist.stream().map(Wishlist::getProduct).collect(Collectors.toList());
    }

    public void deleteWishlistItem(String email, Long productId) {
        Wishlist wish = wishlistRepository.findByMemberEmailAndProductId(email, productId);
        if (wish != null) {
            wishlistRepository.delete(wish);
        }
    }
}
