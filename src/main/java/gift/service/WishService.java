package gift.service;

import gift.controller.wish.dto.WishRequest;
import gift.controller.wish.dto.WishResponse;
import gift.global.dto.PageResponse;
import gift.model.member.Member;
import gift.model.product.Product;
import gift.model.wish.Wish;
import gift.repository.member.MemberRepository;
import gift.repository.product.ProductJpaRepository;
import gift.repository.wish.WishJpaRepository;
import gift.global.validate.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishJpaRepository wishJpaRepository;
    private final MemberRepository memberRepository;
    private final ProductJpaRepository productJpaRepository;

    public WishService(WishJpaRepository wishJpaRepository, MemberRepository memberRepository,
        ProductJpaRepository productJpaRepository) {
        this.wishJpaRepository = wishJpaRepository;
        this.memberRepository = memberRepository;
        this.productJpaRepository = productJpaRepository;
    }

    //@Transactional
    public void addWish(Long userId, WishRequest.Register request) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Member not found"));

        Product product = productJpaRepository.findById(request.productId())
            .orElseThrow(() -> new NotFoundException("Product not found"));

        wishJpaRepository.findByMemberAndProduct(member, product)
            .ifPresent(wish -> {
                throw new IllegalArgumentException("Wish already exists");
            });

        wishJpaRepository.save(request.toEntity(member, product));
    }

    //@Transactional
    public void updateWish(Long userId, WishRequest.Update request) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Member not found"));

        Product product = productJpaRepository.findById(request.productId())
            .orElseThrow(() -> new NotFoundException("Product not found"));

        Wish wish = wishJpaRepository.findByMemberAndProduct(member, product)
            .orElseThrow(() -> new NotFoundException("Wish not found"));

        wish.updateCount(request.count());
    }

    //@Transactional
    public void deleteWish(Long memberId, Long wishId) {
        Wish wish = wishJpaRepository.findById(wishId)
            .orElseThrow(() -> new NotFoundException("Wish not found"));

        if (wish.isOwner(memberId)) {
            wishJpaRepository.deleteById(wishId);
            return;
        }

        throw new IllegalArgumentException("본인의 위시리스트만 삭제 가능합니다.");
    }

    @Transactional(readOnly = true)
    public PageResponse<WishResponse.Info> getWishesPaging(Long memberId, Pageable pageable) {
        Page<Wish> wishPage = wishJpaRepository.findAllByMemberByIdDesc(memberId, pageable);
        var content = wishPage.getContent().stream()
            .map(WishResponse.Info::from)
            .toList();

        return PageResponse.from(content, wishPage);
    }
}
