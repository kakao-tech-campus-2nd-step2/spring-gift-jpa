package gift.service;

import gift.repository.MemberRepository;
import gift.repository.WishProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final WishProductRepository wishProductRepository;

    public MemberService(MemberRepository memberRepository, WishProductRepository wishProductRepository) {
        this.memberRepository = memberRepository;
        this.wishProductRepository = wishProductRepository;
    }

    public void deleteMember(Long memberId) {
        wishProductRepository.deleteWishProductsByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
