package gift.api.wishlist;

import gift.api.member.Member;
import gift.api.member.MemberRepository;
import gift.api.product.Product;
import gift.api.product.ProductRepository;
import gift.global.exception.NoSuchIdException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    public WishService(MemberRepository memberRepository, ProductRepository productRepository,
                                                        WishRepository wishRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
    }

    public List<Wish> getItems(Long memberId, int page, int size, String criterion, String direction) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchIdException("member"));
        Pageable pageRequest = PageRequest.of(page, size,
            Sort.by(Direction.fromOptionalString(direction).orElse(Direction.ASC), "product."+criterion));
        Page<Wish> allWishes = wishRepository.findAllByMember(member, pageRequest);
        return allWishes.hasContent() ? allWishes.getContent() : Collections.emptyList();
    }

    public void add(Long memberId, WishRequest wishRequest) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchIdException("member"));
        Product product = productRepository.findById(wishRequest.productId())
            .orElseThrow(() -> new NoSuchIdException("product"));
        wishRepository.save(new Wish(member, product, wishRequest.quantity()));
    }

    @Transactional
    public void update(Long memberId, WishRequest wishRequest) {
        Wish wish = wishRepository.findById(new WishId(memberId, wishRequest.productId()))
            .orElseThrow(() -> new NoSuchIdException("wish"));
        wish.updateQuantity(wishRequest.quantity());
    }

    public void delete(Long memberId, WishRequest wishRequest) {
        wishRepository.deleteById(new WishId(memberId, wishRequest.productId()));
    }
}
