package gift.wishlist;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;
import static gift.exception.ErrorMessage.WISHLIST_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.WISHLIST_NOT_FOUND;

import gift.member.Member;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.token.MemberTokenDTO;
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

    public List<Product> getAllWishlists(MemberTokenDTO memberTokenDTO) {
        return wishlistRepository
            .findAllByMember(Member.fromMemberWithoutBody(memberTokenDTO))
            .stream()
            .map(Wishlist::getProduct)
            .toList();
    }

    public void addWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        if (isWishlistExists(memberTokenDTO, productId)) {
            throw new IllegalArgumentException(WISHLIST_ALREADY_EXISTS);
        }

        wishlistRepository.save(
            new Wishlist(
                Product.fromProductIdWithoutBody(productId),
                Member.fromMemberWithoutBody(memberTokenDTO)
            )
        );
    }

    private boolean isWishlistExists(MemberTokenDTO memberTokenDTO, long productId) {
        return wishlistRepository.existsByMemberAndProduct(
            Member.fromMemberWithoutBody(memberTokenDTO),
            Product.fromProductIdWithoutBody(productId)
        );
    }

    public void deleteWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        Wishlist findWishlist = wishlistRepository.findByMemberAndProduct(
            Member.fromMemberWithoutBody(memberTokenDTO),
            Product.fromProductIdWithoutBody(productId)
        );

        if (findWishlist == null) {
            throw new IllegalArgumentException(WISHLIST_NOT_FOUND);
        }

        wishlistRepository.delete(findWishlist);
    }
}
