package gift.wishlist.application;

import gift.member.error.MemberNotFoundException;
import gift.product.error.ProductNotFoundException;
import gift.wishlist.error.WishAlreadyExistsException;
import gift.wishlist.error.WishNotFoundException;
import gift.member.dao.MemberRepository;
import gift.member.entity.Member;
import gift.product.dao.ProductRepository;
import gift.product.entity.Product;
import gift.wishlist.dao.WishesRepository;
import gift.wishlist.entity.Wish;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(WishNotFoundException::new);

        wishesRepository.delete(wish);
    }

    public List<Product> getWishlistOfMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new)
                .getWishList()
                .stream()
                .map(Wish::getProduct)
                .toList();
    }

    private Wish createWish(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return new Wish(member, product);
    }

}
