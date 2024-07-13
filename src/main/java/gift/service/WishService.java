package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.MemberNotFoundException;
import gift.exception.WishNotFoundException;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    private final String NOT_FOUND_MEMBER_BY_EMAIL_MESSAGE = "해당 email을 가진 member가 존재하지 않습니다";
    private final String NOT_FOUND_WISH_MESSAGE = "해당 wish가 존재하지 않습니다.";

    public WishService(WishRepository wishRepository, MemberRepository memberRepository,
        ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void save(String memberEmail, WishRequestDto wishRequestDto) {
        Member member = memberRepository.findByEmail(memberEmail).get();
        Product product = productRepository.findById(wishRequestDto.getProductId()).get();
        wishRepository.save(new Wish(member, product, wishRequestDto.getQuantity()));
    }

    public List<WishResponseDto> findByEmail(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER_BY_EMAIL_MESSAGE));

        List<Wish> wishes = wishRepository.findByMemberId(member.getId())
            .orElseThrow(() -> new WishNotFoundException(NOT_FOUND_WISH_MESSAGE));

        return wishes.stream()
            .map(this::convertToWishDto)
            .collect(Collectors.toList());
    }

    private WishResponseDto convertToWishDto(Wish wish) {
        return new WishResponseDto(
            wish.getId(),
            wish.getMember().getId(),
            wish.getProduct().getId(),
            wish.getQuantity()
        );
    }

    @Transactional
    public void deleteWish(String memberEmail, Long productId) {
        Member member = memberRepository.findByEmail(memberEmail).get();
        wishRepository.deleteById(member.getId());
    }

    public void updateWish(String memberEmail, Long productId, int quantity) {
        Member member = memberRepository.findByEmail(memberEmail).get();
        Wish updateWish = wishRepository.findByMemberIdAndProductId(member.getId(),productId).get();
        updateWish.setQuantity(quantity);
        wishRepository.save(updateWish);
    }

    public  List<WishResponseDto> findByEmailPage(String memberEmail, Pageable pageable){
        Member member = memberRepository.findByEmail(memberEmail)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER_BY_EMAIL_MESSAGE));
        List<Wish> wishes = wishRepository.findAllByMemberIdOrderByCreatedAt(member.getId(), pageable);
        return wishes.stream()
            .map(this::convertToWishDto)
            .collect(Collectors.toList());
    }
}
