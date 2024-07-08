package gift.service;

import gift.controller.wish.dto.WishRequest;
import gift.controller.wish.dto.WishResponse;
import gift.model.wish.Wish;
import gift.repository.MemberJpaRepository;
import gift.repository.ProductJpaRepository;
import gift.repository.WishJpaRepository;
import gift.validate.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishJpaRepository wishJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public WishService(WishJpaRepository wishJpaRepository, MemberJpaRepository memberJpaRepository,
        ProductJpaRepository productJpaRepository) {
        this.wishJpaRepository = wishJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    @Transactional
    public void addWish(Long userId, WishRequest.Register request) {
        System.out.println("userId = " + userId);
        var member = memberJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Member not found"));

        var product = productJpaRepository.findById(request.productId())
            .orElseThrow(() -> new NotFoundException("Product not found"));

        wishJpaRepository.findByMemberAndProduct(member, product)
            .ifPresent(wish -> {
                throw new IllegalArgumentException("Wish already exists");
            });

        wishJpaRepository.save(request.toEntity(member, product));
    }

    @Transactional
    public void updateWish(Long userId, WishRequest.Update request) {
        var member = memberJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Member not found"));

        var product = productJpaRepository.findById(request.productId())
            .orElseThrow(() -> new NotFoundException("Product not found"));

        var wish = wishJpaRepository.findByMemberAndProduct(member, product)
            .orElseThrow(() -> new NotFoundException("Wish not found"));

        wish.updateCount(request.count());
    }

    @Transactional
    public void deleteWish(Long memberId, Long wishId) {
        Wish wish = wishJpaRepository.findById(wishId)
            .orElseThrow(() -> new NotFoundException("Wish not found"));

        System.out.println("wish.getMember().getId() = " + wish.getMember().getId());
        System.out.println("memberId = " + memberId);
        if (!wish.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 위시리스트만 삭제 가능합니다.");
        }

        wishJpaRepository.deleteById(wishId);
    }

    @Transactional(readOnly = true)
    public List<WishResponse.Info> getWishes(Long memberId) {
        var response = wishJpaRepository.findByMemberId(memberId)
            .stream()
            .map(WishResponse.Info::from)
            .collect(Collectors.toList());
        return response;
    }
}
