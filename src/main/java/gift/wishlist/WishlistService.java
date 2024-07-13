package gift.wishlist;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;
import static gift.exception.ErrorMessage.WISHLIST_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.WISHLIST_NOT_FOUND;

import gift.member.Member;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.token.MemberTokenDTO;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    public WishlistService(
        WishlistRepository wishlistRepository,
        ProductRepository productRepository,
        EntityManager entityManager
    ) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    public Page<Product> getAllWishlists(MemberTokenDTO memberTokenDTO, Pageable pageable) {
        return wishlistRepository
            .findAllByMemberEmail(memberTokenDTO.getEmail(), pageable)
            .map(Wishlist::getProduct);
    }

    public List<Product> getAllWishlists(MemberTokenDTO memberTokenDTO) {
        return wishlistRepository.findAllByMemberEmail(memberTokenDTO.getEmail())
            .stream()
            .map(Wishlist::getProduct)
            .toList();
    }

    public void addWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));

        wishlistRepository.findByMemberEmailAndProductId(memberTokenDTO.getEmail(), productId)
            .ifPresent(
                e -> {
                    throw new IllegalArgumentException(WISHLIST_ALREADY_EXISTS);
                }
            );

        wishlistRepository.save(
            new Wishlist(
                product,
                entityManager.getReference(Member.class, memberTokenDTO.getEmail())
            )
        );
    }

    public void deleteWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        Wishlist findWishlist = wishlistRepository.findByMemberEmailAndProductId(
            memberTokenDTO.getEmail(), productId
        ).orElseThrow(() -> new IllegalArgumentException(WISHLIST_NOT_FOUND));

        wishlistRepository.delete(findWishlist);
    }
}
