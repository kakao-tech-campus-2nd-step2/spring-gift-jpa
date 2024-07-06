package gift.member.business.service;

import gift.global.authentication.jwt.JwtGenerator;
import gift.global.authentication.jwt.JwtToken;
import gift.global.exception.ErrorCode;
import gift.global.exception.LoginException;
import gift.member.business.dto.MemberRegisterDto;
import gift.member.business.dto.WishListDto;
import gift.member.persistence.entity.Wishlist;
import gift.member.persistence.repository.MemberRepository;
import gift.member.business.dto.MemberLoginDto;
import gift.member.persistence.repository.WishlistRepository;
import gift.member.presentation.dto.WishlistUpdateDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtGenerator jwtGenerator;
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public MemberService(MemberRepository memberRepository, JwtGenerator jwtGenerator,
        WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.jwtGenerator = jwtGenerator;
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public JwtToken registerMember(MemberRegisterDto memberRegisterDto) {
        var member = memberRegisterDto.toMember();
        var id = memberRepository.saveMember(member);
        return jwtGenerator.createToken(id);
    }

    public JwtToken loginMember(MemberLoginDto memberLoginDto) {
        var member = memberRepository.getMemberByEmail(memberLoginDto.email());
        if (!member.getPassword().equals(memberLoginDto.password())) {
            throw new LoginException(ErrorCode.LOGIN_ERROR, "패스워드가 이메일과 일치하지 않습니다.");
        }
        return jwtGenerator.createToken(member.getId());
    }

    public List<WishListDto> getWishLists(Long memberId) {
        var wishLists = wishlistRepository.getWishListByMemberId(memberId);

        var productIds = wishLists.stream()
            .map(Wishlist::getProductId)
            .toList();

        Map<Long, Product> products = productRepository.getProductsByIds(productIds);

        return wishLists.stream()
            .map(wishlist -> WishListDto.of(
                wishlist.getId(),
                products.get(wishlist.getProductId()),
                wishlist.getCount()
            ))
            .toList();
    }

    public Long addWishList(Long memberId, Long productId) {
        var wishList = new Wishlist(productId, memberId, 1);
        return wishlistRepository.saveWishList(wishList);
    }

    public Long updateWishList(Long memberId, WishlistUpdateDto wishListUpdateDto) {
        var wishList = wishlistRepository.getWishListByMemberIdAndProductId(
            memberId, wishListUpdateDto.productId());

        wishList.setCount(wishListUpdateDto.count());
        return wishlistRepository.updateWishlist(wishList);
    }

    public void deleteWishList(Long memberId, Long productId) {
        wishlistRepository.deleteWishlist(memberId, productId);
    }
}
