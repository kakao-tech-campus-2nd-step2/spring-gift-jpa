package gift.wishlist;

import gift.product.Product;
import gift.product.ProductRepository;
import gift.token.MemberTokenDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(
        WishlistRepository wishlistRepository,
        ProductRepository productRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getAllWishlists(MemberTokenDTO memberTokenDTO) {
        return wishlistRepository.findAllByMemberEmail(memberTokenDTO.getEmail())
            .stream()
            .map(e -> getProductById(e.getProduct().getId()))
            .toList();
    }

    public void addWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        if (wishlistRepository.existsByMemberEmailAndProductId(memberTokenDTO.getEmail(),
            productId)) {
            throw new IllegalArgumentException("Wishlist already exists");
        }
        wishlistRepository.save(
            new Wishlist(
                getProductById(productId),
                memberTokenDTO.getEmail()
            )
        );
    }

    public void deleteWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        wishlistRepository.delete(
            new Wishlist(
                getProductById(productId),
                memberTokenDTO.getEmail()
            )
        );
    }

    private Product getProductById(long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product does not exist");
        }
        return product.get();
    }
}
