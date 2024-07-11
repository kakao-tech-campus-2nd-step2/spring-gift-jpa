package gift.wishlist;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;
import static gift.exception.ErrorMessage.WISHLIST_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.WISHLIST_NOT_FOUND;

import gift.member.Member;
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
        return wishlistRepository
            .findAllByMember(new Member(memberTokenDTO.getEmail(), null))
            .stream()
            .map(e -> getProductById(e.getProduct().getId()))
            .toList();
    }

    public void addWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        if (wishlistRepository.existsByMemberAndProduct(
            new Member(memberTokenDTO.getEmail(), null),
            getProductById(productId))
        ) {
            throw new IllegalArgumentException(WISHLIST_ALREADY_EXISTS);
        }

        wishlistRepository.save(
            new Wishlist(
                getProductById(productId),
                new Member(memberTokenDTO.getEmail(), null)
            )
        );
    }

    public void deleteWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        Wishlist findWishlist = wishlistRepository.findByMemberAndProduct(
            new Member(memberTokenDTO.getEmail(), null),
            new Product(productId, null, -1, null)
        );

        if (findWishlist == null) {
            throw new IllegalArgumentException(WISHLIST_NOT_FOUND);
        }

        wishlistRepository.delete(findWishlist);
    }

    private Product getProductById(long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        return product.get();
    }
}
