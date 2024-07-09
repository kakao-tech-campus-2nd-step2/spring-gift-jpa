package gift.controller;

import gift.config.LoginMember;
import gift.dto.UserRequestDTO;
import gift.model.WishList;
import gift.service.WishListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 사용자의 위시 리스트를 조회
    @GetMapping
    public List<WishList> getWishlist(@LoginMember UserRequestDTO userRequestDTO) {
        return wishListService.getWishlist(userRequestDTO);
    }

    // 위시 리스트에 상품을 추가
    @PostMapping
    public void addProductToWishlist(@LoginMember UserRequestDTO userRequestDTO, @RequestParam Long productId) {
        wishListService.addProductToWishlist(userRequestDTO, productId);
    }

    // 위시 리스트에서 상품을 삭제
    @DeleteMapping("/{productId}")
    public void removeProductFromWishlist(@LoginMember UserRequestDTO userRequestDTO, @PathVariable Long productId) {
        wishListService.removeProductFromWishlist(userRequestDTO, productId);
    }
}
