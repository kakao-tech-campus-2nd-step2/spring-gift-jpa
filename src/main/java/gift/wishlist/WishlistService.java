package gift.wishlist;

import gift.member.MemberDTO;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
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

    public List<Product> getAllWishlists(MemberDTO memberDTO) {
        return wishlistRepository.findAllByMemberEmail(memberDTO.email())
            .stream()
            .map(e -> productRepository.findById(e.getProductId()).get())
            .toList();
    }

    public void addWishlist(MemberDTO memberDTO, long productId) {
        if (wishlistRepository.existsByMemberEmailAndProductId(memberDTO.email(), productId)) {
            throw new IllegalArgumentException("Wishlist already exists");
        }
        hasProductByProductID(productId);
        wishlistRepository.save(new Wishlist(productId, memberDTO.email()));
    }

    public void deleteWishlist(MemberDTO memberDTO, long productId) {
        hasProductByProductID(productId);
        wishlistRepository.delete(new Wishlist(productId, memberDTO.email()));
    }

    private void hasProductByProductID(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product does not exist");
        }
    }
}
