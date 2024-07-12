package gift.wishlist.application;

import gift.error.CustomException;
import gift.error.ErrorCode;
import gift.member.dao.MemberRepository;
import gift.member.entity.Member;
import gift.product.dao.ProductRepository;
import gift.product.entity.Product;
import gift.wishlist.dao.WishesRepository;
import gift.wishlist.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishesService {

    private final WishesRepository wishesRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishesService(WishesRepository wishesRepository,
                         MemberRepository memberRepository,
                         ProductRepository productRepository) {
        this.wishesRepository = wishesRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void addProductToWishlist(Long memberId, Long productId) {
        wishesRepository.findByMember_IdAndProduct_Id(memberId, productId)
                .ifPresent(wish -> {
                    throw new CustomException(ErrorCode.WISH_ALREADY_EXISTS);
                });

        wishesRepository.save(createWish(memberId, productId));
    }

    public void removeProductFromWishlist(Long memberId, Long productId) {
        Wish wish = wishesRepository.findByMember_IdAndProduct_Id(memberId, productId)
                .orElseThrow(() -> new CustomException(ErrorCode.WISH_NOT_FOUND));

        wishesRepository.delete(wish);
    }

    public Page<Product> getWishlistOfMember(Long memberId, Pageable pageable) {
        return wishesRepository.findByMember_Id(memberId, pageable)
                .map(Wish::getProduct);
    }

    private Wish createWish(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return new Wish(member, product);
    }

}
