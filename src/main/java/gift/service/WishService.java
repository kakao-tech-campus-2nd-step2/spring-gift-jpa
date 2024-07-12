package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.WishDTO;
import gift.repository.WishRepository;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void addWish(Long memberId, String productName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!wishRepository.existsByMemberAndProduct(member, product)) {
            Wish wish = new Wish();
            wish.setMember(member);
            wish.setProduct(product);
            wishRepository.save(wish);
        }
    }

    public List<Wish> getWishes(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return wishRepository.findByMember(member);
    }

    public void removeWish(Long memberId, String productName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        wishRepository.deleteByMemberAndProduct(member, product);
    }
}
