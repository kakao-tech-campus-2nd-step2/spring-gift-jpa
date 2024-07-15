package gift.service;

import gift.controller.wish.dto.WishRequest;
import gift.controller.wish.dto.WishResponse;
import gift.global.dto.PageResponse;
import gift.model.member.Member;
import gift.model.product.Product;
import gift.model.wish.Wish;
import gift.repository.member.MemberRepository;
import gift.repository.product.ProductRepository;
import gift.repository.wish.WishJpaRepository;
import gift.global.validate.NotFoundException;
import gift.repository.wish.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishJpaRepository wishRepository, MemberRepository memberRepository,
        ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    //@Transactional
    public void addWish(Long userId, WishRequest.Register request) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Member not found"));

        Product product = productRepository.findById(request.productId())
            .orElseThrow(() -> new NotFoundException("Product not found"));

        wishRepository.findByMemberAndProduct(member, product)
            .ifPresent(wish -> {
                throw new IllegalArgumentException("Wish already exists");
            });

        wishRepository.save(request.toEntity(member, product));
    }

    @Transactional
    public void updateWish(Long userId, WishRequest.Update request) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Member not found"));

        Product product = productRepository.findById(request.productId())
            .orElseThrow(() -> new NotFoundException("Product not found"));

        Wish wish = wishRepository.findByMemberAndProduct(member, product)
            .orElseThrow(() -> new NotFoundException("Wish not found"));

        wish.updateCount(request.count());
    }

    //@Transactional
    public void deleteWish(Long memberId, Long wishId) {
        Wish wish = wishRepository.findById(wishId)
            .orElseThrow(() -> new NotFoundException("Wish not found"));

        if (wish.isOwner(memberId)) {
            wishRepository.deleteById(wishId);
            return;
        }

        throw new IllegalArgumentException("본인의 위시리스트만 삭제 가능합니다.");
    }

    @Transactional(readOnly = true)
    public PageResponse<WishResponse.Info> getWishesPaging(Long memberId, Pageable pageable) {
        Page<Wish> wishPage = wishRepository.findAllByMemberByIdDesc(memberId, pageable);

        return PageResponse.from(wishPage, WishResponse.Info::from);
    }
}
