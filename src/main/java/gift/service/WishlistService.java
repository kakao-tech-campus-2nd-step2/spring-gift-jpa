package gift.service;

import gift.entity.Product;
import gift.entity.Wishlist;
import gift.repository.WishlistRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public Page<Product> getWishlistByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Wishlist> wishlist = wishlistRepository.findByMemberEmail(email, pageable);
        return wishlist.map(Wishlist::getProduct);
    }

    public void deleteWishlistItem(String email, Long productId) {
        Wishlist wish = wishlistRepository.findByMemberEmailAndProductId(email, productId);
        if (wish != null) {
            wishlistRepository.delete(wish);
        }
    }
}
