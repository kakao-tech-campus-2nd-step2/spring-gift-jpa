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
        return wishlistRepository.getAllWishlists(memberDTO.email());
    }

    public void addWishlist(MemberDTO memberDTO, long productId) {
        if (wishlistRepository.existWishlist(new Wishlist(productId, memberDTO.email()))) {
            throw new IllegalArgumentException("Wishlist already exists");
        }
        hasProductByProductID(productId);
        wishlistRepository.addWishlist(new Wishlist(productId, memberDTO.email()));
    }

    public void deleteWishlist(MemberDTO memberDTO, long productId) {
        hasProductByProductID(productId);
        wishlistRepository.deleteWishlist(new Wishlist(productId, memberDTO.email()));
    }

    private void hasProductByProductID(long productId) {
        if (!productRepository.existProduct(productId)) {
            throw new IllegalArgumentException("Product does not exist");
        }
    }
}
