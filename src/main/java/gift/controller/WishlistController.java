package gift.controller;

import gift.dto.Wishlist;
import gift.dto.Wishlist.Response;
import gift.service.WishlistService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // 전체 장바구니 조회
    @GetMapping("/api/wishlist")
    @ResponseBody
    public List<Wishlist.Response> getAllWishlistItems(@RequestHeader("Authorization") String accessToken){
        long userId = wishlistService.findUserId(accessToken);

        List<Response> responseList = wishlistService.getAllWishlistItems(userId);
        return responseList;
    }

    // 장바구니 상품 추가
    @PostMapping("/api/wishlist")
    @ResponseBody
    public Wishlist.Response addItem(@RequestHeader("Authorization") String accessToken, @RequestBody Wishlist.Request request){
        long userId = wishlistService.findUserId(accessToken);

        Wishlist.Response item = wishlistService.addItemToWishlist(userId, request);
        return item;
    }
    // 장바구니 상품 삭제
    @DeleteMapping("/api/wishlist")
    @ResponseBody
    public Wishlist.Response deleteItem(@RequestHeader("Authorization") String accessToken, @RequestBody Wishlist.Request request){
        long userId = wishlistService.findUserId(accessToken);

        Wishlist.Response item = wishlistService.deleteItemFromWishlist(userId, request);
        return item;
    }
}
