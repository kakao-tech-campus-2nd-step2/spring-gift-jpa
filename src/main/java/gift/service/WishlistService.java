package gift.service;

import gift.model.Member;
import gift.model.WishList;
import gift.repository.MemberRepository;
import gift.repository.WishlistRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
    }

    public WishList addProduct(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        WishList wishlist = new WishList(member, productId);
        return wishlistRepository.save(wishlist);

    }

    public List<WishList> getProductsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return wishlistRepository.findByMember(member);
    }

    public void deleteById(Long productId) {
        wishlistRepository.deleteById(productId);
    }
}
