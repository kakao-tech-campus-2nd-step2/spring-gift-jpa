package gift.service;

import gift.exception.ResourceNotFoundException;
import gift.model.Product;
import gift.model.Wishlist;
import gift.model.WishlistDTO;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Set<Product> getWishlistProducts(String email) {
        Optional<Wishlist> wishlist = wishlistRepository.findByEmail(email);
        if (wishlist.isEmpty()) {
            Wishlist save = wishlistRepository.save(new Wishlist());
            wishlist = Optional.of(save);
        }
        return wishlist.get().getProducts();
    }

    public void addWishlistProduct(String email, WishlistDTO wishlistDTO) {
        Long id = wishlistDTO.getProductId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        Optional<Wishlist> wishlist = wishlistRepository.findByEmail(email);

        if (wishlist.isEmpty()) {
            Wishlist saved = wishlistRepository.save(new Wishlist(email));
            wishlist = Optional.of(saved);
        }

        wishlist.get().addProduct(product);

        wishlistRepository.save(wishlist.get());
    }

    public void deleteWishlist(String email) {
        Optional<Wishlist> wishlist = wishlistRepository.findByEmail(email);
        if (wishlist.isEmpty()) {
            return;
        }
        wishlistRepository.delete(wishlist.get());
    }
}
