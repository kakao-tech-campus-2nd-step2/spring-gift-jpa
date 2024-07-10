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
            .map(e -> productRepository.findById(e.getProductId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }

    public void addWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        if (wishlistRepository.existsByMemberEmailAndProductId(memberTokenDTO.getEmail(),
            productId)) {
            throw new IllegalArgumentException("Wishlist already exists");
        }
        hasProductByProductID(productId);
        wishlistRepository.save(new Wishlist(productId, memberTokenDTO.getEmail()));
    }

    public void deleteWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        hasProductByProductID(productId);
        wishlistRepository.delete(new Wishlist(productId, memberTokenDTO.getEmail()));
    }

    private void hasProductByProductID(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product does not exist");
        }
    }
}
