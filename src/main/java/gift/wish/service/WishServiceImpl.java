package gift.wish.service;

import gift.member.model.Member;
import gift.product.model.Product;
import gift.wish.model.Wish;
import gift.member.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.wish.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishServiceImpl(WishRepository wishlistRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void addProductToWishlist(Member member, Product product) {
        // Product 유효성 검증 및 저장
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            productRepository.save(product);
        }

        // Wish 생성 및 연관관계 설정
        Wish wish = new Wish(member, product);

        // Wish 저장
        wishRepository.save(wish);

        // member의 List<Wish>에 wish 추가
        member.addWish(wish);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wish> getWishlistByMemberId(Member member) {
        return member.getWishList();
    }

    @Override
    @Transactional
    public void updateProductInWishlist(Long memberId, Long productId, Product updatedProduct) {
        Wish wish = wishRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for memberId: " + memberId + " and productId: " + productId));

        Product product = wish.getProduct();
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setImageUrl(updatedProduct.getImageUrl());

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeProductFromWishlist(Long memberId, Long productId) {
        Wish wish = wishRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for memberId: " + memberId + " and productId: " + productId));

        wishRepository.delete(wish);
    }
}
