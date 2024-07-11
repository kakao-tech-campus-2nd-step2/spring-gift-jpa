package gift.service;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.MemberNotFoundException;
import gift.exception.WishNotFoundException;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;

    private final String NOT_FOUND_USER_BY_EMAIL_MESSAGE = "해당 email을 가진 user가 존재하지 않습니다";
    private final String NOT_FOUND_WISH_MESSAGE = "해당 wish가 존재하지 않습니다.";

    public WishService(WishRepository wishRepository, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
    }

    public void save(String memberEmail, WishRequestDto wishRequestDto) {
        Member member = memberRepository.findByEmail(memberEmail).get();
        wishRepository.save(new Wish(member, wishRequestDto.getProduct(), wishRequestDto.getQuantity()));
    }

    public List<WishResponseDto> findByEmail(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_USER_BY_EMAIL_MESSAGE));

        List<Wish> wishes = wishRepository.findByMemberId(member.getId())
            .orElseThrow(() -> new WishNotFoundException(NOT_FOUND_WISH_MESSAGE));

        return wishes.stream()
            .map(this::convertToWishDto)
            .collect(Collectors.toList());
    }

    private WishResponseDto convertToWishDto(Wish wish) {
        return new WishResponseDto(
            wish.getId(),
            wish.getMember(),
            wish.getProduct(),
            wish.getQuantity()
        );
    }

    @Transactional
    public void deleteWish(String userEmail, Long productId) {
        Member member = memberRepository.findByEmail(userEmail).get();
        wishRepository.deleteById(member.getId());
    }

    public void updateWish(String userEmail, Long productId, int quantity) {
        Member member = memberRepository.findByEmail(userEmail).get();
        Wish updateWish = wishRepository.findByMemberIdAndProductId(member.getId(),productId).get();
        updateWish.setQuantity(quantity);
        wishRepository.save(updateWish);
    }
}
