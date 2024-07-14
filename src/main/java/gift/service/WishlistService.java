package gift.service;

import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository, ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<Product> getWishlist(String email) {
        List<Wishlist> wishlistItems = wishlistRepository.findByMemberEmail(email);
        return wishlistItems.stream()
            .map(item -> productService.findProductsById(item.getProductId()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void addWishlist(String email, Long productId) {
        Wishlist wishlist = new Wishlist(null, email, productId);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeWishlist(String email, Long productId) {
        wishlistRepository.deleteByMemberEmailAndProductId(email, productId);
    }
}