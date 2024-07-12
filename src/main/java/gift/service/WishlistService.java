package gift.service;

import gift.entity.Member;
import gift.entity.Wish;
import gift.exception.DataNotFoundException;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class WishlistService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final int PAGE_SIZE = 5;

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


    public void deleteWishlist(Long id, String email) {
        Member authMember = memberRepository.findByEmail(email);
        Optional<Wish> wish = wishRepository.findById(id);
        if (wish.isEmpty()) {
            throw new DataNotFoundException("존재하지 않는 Wishlist: wishList를 삭제할 수 없습니다.");
        }
        Wish deletingWish = wish.get();

        //삭제하려는 Wish의 MemberId와 accessToken에서 받은 memberId가 같아야 삭제
        if (authMember.getId().equals(deletingWish.getMember().getId())) {
            wishRepository.deleteById(id);
        }

    }

    public List<Wish> getWishlistByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return wishRepository.findByMemberId(member.getId());
    }

    public Page<Wish> getWishPage(String email, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page,PAGE_SIZE,Sort.by(sorts));
        Member member = memberRepository.findByEmail(email);
        return wishRepository.findByMemberId(member.getId(),pageable);

    }
}
