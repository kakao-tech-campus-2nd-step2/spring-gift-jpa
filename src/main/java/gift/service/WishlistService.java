package gift.service;

import gift.dto.WishlistDTO;
import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import gift.repository.UserRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, ProductRepository productRepository){
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void addWishlist(WishlistDTO wishlistDTO){
        User user = userRepository.findByEmail(wishlistDTO.getUserId());
        Product product = productRepository.findById(wishlistDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + wishlistDTO.getProductId()));

        Wishlist wishlist = new Wishlist(user, product);
        wishlistRepository.save(wishlist);
    }

    public List<WishlistDTO> loadWishlist(String userId){
        return wishlistRepository.findByUserEmail(userId)
                .stream()
                .map(wishlist -> new WishlistDTO(wishlist.getUser().getEmail(), wishlist.getProduct().getId()))
                .collect(Collectors.toList());
    }

    public void deleteWishlist(String userId, Long productId){
        wishlistRepository.deleteByUserEmailAndProductId(userId, productId);
    }

    public List<Product> getProductsFromWishlist(List<WishlistDTO> wishlist) {
        return wishlist.stream()
                .map(item -> productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + item.getProductId())))
                .collect(Collectors.toList());
    }
}