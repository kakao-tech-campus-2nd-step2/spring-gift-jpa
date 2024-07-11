package gift.wish.service;

import gift.member.model.Member;
import gift.product.model.Product;
import gift.wish.model.Wish;
import gift.wish.model.WishDTO;
import gift.member.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.wish.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishServiceImpl(WishRepository wishlistRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void addProductToWishlist(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id: " + productId));

        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishDTO> getWishlistByMemberId(Member member) {
        List<Wish> wishlist = wishRepository.findAllByMember(member);
        return wishlist.stream()
                .map(wish -> new WishDTO(
                        wish.getId(),
                        wish.getProduct().getId(),
                        wish.getProduct().getName(),
                        wish.getProduct().getPrice(),
                        wish.getProduct().getImageUrl()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteWish(Long wishId) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for id: " + wishId));
        wishRepository.delete(wish);
    }
}
