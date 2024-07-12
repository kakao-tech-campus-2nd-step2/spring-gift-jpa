package gift.wishlist.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import gift.wishlist.model.WishList;
import gift.wishlist.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<WishList> getWishListByMemberId(Long memberId) {
        return wishListRepository.findByMemberId(memberId);
    }

    public WishList addProductToWishList(Long memberId, String name, String product) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        member.addWishList(name, product);
        return wishListRepository.save(new WishList(member, name, product));
    }

    public void removeProductFromWishList(Long wishListId) {
        wishListRepository.deleteById(wishListId);
    }

    public Long getMemberIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email"));
        return member.id();
    }

    public WishList getWishListById(Long id) {
        return wishListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid wish list ID"));
    }

    public WishList updateProductInWishList(Long id, String product) {
        WishList wishList = getWishListById(id);
        return wishListRepository.save(new WishList(wishList.getMember(), wishList.name(), product));
    }
}