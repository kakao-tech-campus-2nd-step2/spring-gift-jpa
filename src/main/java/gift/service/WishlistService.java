package gift.service;

import gift.entity.Member;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class WishlistService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishRepository wishRepository, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
    }

    public void addWishlist(Wish wish, String email) {
        Member wishMember = wish.getMember();
        Member authMember = memberRepository.findByEmail(email);
        if (wishMember.getId().equals(authMember.getId())) {
            wishRepository.save(wish);
        }

    }

    public List<Wish> getWishlist(Long id) {
        return wishRepository.findByMemberId(id);
    }

    public void deleteWishlist(Long id, String email) {
        Member authMember = memberRepository.findByEmail(email);
        Wish deletingWish = wishRepository.findById(id).get();

        //삭제하려는 Wish의 MemberId와 accessToken에서 받은 memberId가 같아야 삭제
        if (authMember.getId().equals(deletingWish.getMember().getId())) {
            wishRepository.deleteById(id);
        }

    }

    public List<Wish> getWishlistByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return wishRepository.findByMemberId(member.getId());
    }


}
