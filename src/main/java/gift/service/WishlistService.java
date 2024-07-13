package gift.service;

import gift.exception.ResourceNotFoundException;
import gift.entity.Product;
import gift.entity.Wishlist;
import gift.entity.WishlistDTO;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Page<Product> getWishlistProducts(String email, Pageable pageable) {
        Page<Product> wishlist = wishlistRepository.findWishlistProductByEmail(email, pageable);
        if (wishlist.isEmpty()) {
            Wishlist save = wishlistRepository.save(new Wishlist(email));

            List<Product> products = save.getProducts().stream().collect(Collectors.toList());
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), products.size());

            return new PageImpl<>(products.subList(start, end), pageable, products.size());
        }
        return wishlist;
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
