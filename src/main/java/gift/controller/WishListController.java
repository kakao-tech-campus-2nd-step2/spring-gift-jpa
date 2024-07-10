package gift.controller;

import gift.ArgumentResolver.LoginMember;
import gift.dto.MemberDTO;
import gift.dto.WishListDTO;
import gift.service.WishListService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/wishlist")
@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public List<WishListDTO> getWishList(@LoginMember MemberDTO memberDTO) {
        return wishListService.getWishList(memberDTO.getId());
    }

    //상품 추가
    @PostMapping
    public void addWishList(@LoginMember MemberDTO memberDTO,
        @RequestBody WishListDTO wishListDTO) {
        //wishListDTO.setMemberId(memberDTO.getId());
        wishListService.addProduct(wishListDTO.getMemberId(), wishListDTO.getProductId());
    }

    //상품 삭제
    @DeleteMapping
    public void deleteWishList(@RequestBody WishListDTO wishListDTO) {
        wishListService.deleteProduct(wishListDTO.getMemberId(), wishListDTO.getProductId());
    }

    //상품 수정
    @PutMapping
    public void updateWishList(@RequestBody WishListDTO wishListDTO) {
        wishListService.updateProduct(wishListDTO.getMemberId(), wishListDTO.getProductId(),
            wishListDTO.getProductValue());
    }


}
