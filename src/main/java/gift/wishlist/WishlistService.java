package gift.wishlist;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;

import gift.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    public WishlistService(
        WishlistRepository wishlistRepository,
        ProductRepository productRepository,
        MemberRepository memberRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public List<Product> getAllWishlists(MemberTokenDTO memberTokenDTO) {
        return wishlistRepository
            .findAllByMember(
                memberRepository.findById(memberTokenDTO.getEmail()).get()
            )
            .stream()
            .map(e -> getProductById(e.getProduct().getId()))
            .toList();
    }

    public void addWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        if (wishlistRepository.existsByMemberAndProduct(
            memberRepository.findById(memberTokenDTO.getEmail()).get(),
            productRepository.findById(productId).get())
        ) {
            throw new IllegalArgumentException("Wishlist already exists");
        }

        wishlistRepository.save(
            new Wishlist(
                getProductById(productId),
                memberRepository.findById(memberTokenDTO.getEmail()).get()
            )
        );
    }

    public void deleteWishlist(MemberTokenDTO memberTokenDTO, long productId) {
        wishlistRepository.delete(
            new Wishlist(
                getProductById(productId),
                memberRepository.findById(memberTokenDTO.getEmail()).get()
            )
        );
    }

    private Product getProductById(long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        return product.get();
    }
}
