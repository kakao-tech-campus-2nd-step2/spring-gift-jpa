package gift.wishlist.application;

import gift.error.WishAlreadyExistsException;
import gift.member.dao.MemberRepository;
import gift.member.entity.Member;
import gift.product.dao.ProductRepository;
import gift.product.entity.Product;
import gift.wishlist.dao.WishesRepository;
import gift.wishlist.entity.Wish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
                    throw new WishAlreadyExistsException();
                });

        wishesRepository.save(createWish(memberId, productId));
    }

    public void removeProductFromWishlist(Long memberId, Long productId) {
        Wish wish = wishesRepository.findByMember_IdAndProduct_Id(memberId, productId)
                .orElseThrow(() -> new NoSuchElementException("해당 위시는 존재하지 않습니다."));

        wishesRepository.delete(wish);
    }

    public List<Product> getWishlistOfMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 회원 계정은 존재하지 않습니다."))
                .getWishList()
                .stream()
                .map(Wish::getProduct)
                .toList();
    }

    private Wish createWish(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 회원 계정은 존재하지 않습니다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품은 존재하지 않습니다."));

        return new Wish(member, product);
    }

}
