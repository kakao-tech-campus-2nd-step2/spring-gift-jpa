package gift.service;

import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository, ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
    }

    public List<Product> getWishlist(String email) {
        List<Wishlist> wishlistItems = wishlistRepository.findByUserEmail(email);
        return wishlistItems.stream()
            .map(item -> productService.findProductsById(item.getProductId()))
            .collect(Collectors.toList());
    }

    public void addWishlist(String email, Long productId) {
        Wishlist wishlist = new Wishlist(null, email, productId);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeWishlist(String email, Long productId) {
        wishlistRepository.deleteByUserEmailAndProductId(email, productId);
    }
}