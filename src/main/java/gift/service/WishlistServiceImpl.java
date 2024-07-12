package gift.service;

import gift.dto.WishlistDTO;
import gift.model.Product;
import gift.model.SiteUser;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository; // 수정: SiteUserRepository -> UserRepository
    private final ProductRepository productRepository;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<WishlistDTO> getWishlistByUser(String username) {
        List<Wishlist> wishlistEntities = wishlistRepository.findByUserUsername(username); // 수정: findByUsername -> findByUser_Username
        return wishlistEntities.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void addToWishlist(String username, Long productId, int quantity) {
        SiteUser user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username)); // 수정: siteUserRepository -> userRepository
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setQuantity(quantity);
        wishlist.setPrice(product.getPrice());
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    @Override
    public void updateQuantity(Long id, int quantity) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid wishlist ID: " + id));
        wishlist.setQuantity(quantity);
        wishlistRepository.save(wishlist);
    }

    private WishlistDTO convertToDTO(Wishlist wishlist) {
        return new WishlistDTO(
            wishlist.getId(),
            wishlist.getProduct().getId(),
            wishlist.getUser().getUsername(),
            wishlist.getQuantity(),
            wishlist.getProduct().getName(),
            wishlist.getPrice(),
            wishlist.getProduct().getImageUrl()
        );
    }

    @Override
    public Page<WishlistDTO> getWishlistByUser1(String username, Pageable pageable) {
        Page<Wishlist> wishlistEntities = wishlistRepository.findByUserUsername(username, pageable);
        return wishlistEntities.map(this::convertToDTO);
    }
}
