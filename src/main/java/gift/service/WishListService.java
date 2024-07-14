package gift.service;


import gift.dto.MemberRequestDTO;
import gift.model.*;
import gift.repository.MemberRepository;
import gift.repository.WishListRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository,
        MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    // 사용자의 위시 리스트를 조회하는 메서드
    public List<WishList> getWishlist(MemberRequestDTO memberRequestDTO) {
        Member member = memberRepository.findByEmail(memberRequestDTO.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return wishListRepository.findByMember(member);
    }

    // 위시 리스트에 상품을 추가하는 메서드
    public void addProductToWishlist(MemberRequestDTO memberRequestDTO, Long productId) {
        Member member = memberRepository.findByEmail(memberRequestDTO.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        WishList wishList = new WishList(member, productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다.")));
        wishListRepository.save(wishList);
    }

    // 위시 리스트에서 상품을 삭제하는 메서드
    public void removeProductFromWishlist(MemberRequestDTO memberRequestDTO, Long wishListId) {
        WishList wishList = wishListRepository.findById(wishListId)
            .orElseThrow(() -> new RuntimeException("위시리스트를 찾을 수 없습니다."));
        wishListRepository.deleteById(wishListId);
    }
}
