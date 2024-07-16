package gift.wishlist.service;

import gift.member.repository.MemberRepository;
import gift.member.service.MemberService;
import gift.product.model.Product;
import gift.wishlist.model.WishList;
import gift.wishlist.repository.WishListRepository;
import gift.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final MemberService memberService;

    @Autowired
    public WishListService(WishListRepository wishListRepository, MemberRepository memberRepository, MemberService memberService) {
        this.wishListRepository = wishListRepository;
        this.memberService = memberService;
    }

    // id로 위시리스트 찾기 (단일 객체 반환)
    public WishList findByMemberId(Long member_id) {
        return wishListRepository.findByMemberId(member_id);
    }

    /** wishListRepository의 findByMemberId 메소드를 호출하여
     데이터베이스에서 페이지네이션된 결과를 가져옴. **/
    public Page<WishList> findByMemberId(Long member_id, Pageable pageable) {
        return wishListRepository.findByMemberId(member_id, pageable);
    }

    // 위시리스트에 상품 추가
    @Transactional
    public void addProductToWishList(Long memberId, Product product) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("옳지않은 회원 id 입니다."));
        WishList wishList = new WishList(member, product);
        wishListRepository.save(wishList);
    }

    // 위시리스트에 상품 삭제
    @Transactional
    public void removeProductFromWishList(Long membe_id, Long produc_id) {
        Optional<WishList> wishListOptional = wishListRepository.findByMemberIdAndProductId(membe_id, produc_id);
        if (wishListOptional.isPresent()) {
            wishListRepository.delete(wishListOptional.get());
        }
    }
}