package gift.service;

import gift.entity.Member;
import gift.entity.Wish;
import gift.dto.wish.WishRequestDTO;
import gift.dto.wish.WishResponseDTO;
import gift.exception.ForbiddenRequestException;
import gift.exception.NoSuchMemberException;
import gift.exception.NoSuchProductException;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
    }

    public List<WishResponseDTO> getWishes(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);

        return wishRepository.findAllByMember(member)
                .stream()
                .map(WishResponseDTO::from)
                .toList();
    }

    public WishResponseDTO addWish(String email, WishRequestDTO wishRequestDTO) {
        long memberId = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new)
                .getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoSuchMemberException::new);


        Wish wish = wishRepository.save(new Wish(
                member,
                wishRequestDTO.productId()
        ));

        return WishResponseDTO.from(wish);
    }

    public void deleteWish(String email, long wishId) {
        long userId = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new)
                .getId();

        long wishOwnerId = wishRepository.findById(wishId)
                .orElseThrow(NoSuchProductException::new)
                .getMemberId();

        if (wishOwnerId != userId) {
            throw new ForbiddenRequestException("user is not owner of wish");
        }

        wishRepository.deleteById(wishId);
    }
}
