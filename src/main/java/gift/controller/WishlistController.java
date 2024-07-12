package gift.controller;

import gift.dto.Wishlist;
import gift.dto.Wishlist.Response;
import gift.service.WishService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class WishlistController {

    private final WishService wishService;

    // 전체 장바구니 조회
    @GetMapping("/api/wishlist")
    @ResponseBody
    public List<Wishlist.Response> getAllWishlistItems(@RequestHeader("Authorization") String accessToken,
                                                        @RequestParam(value="page",defaultValue = "0") int page,
                                                        @RequestParam(value="size",defaultValue = "10") int size){
//        long userId = wishlistService.findUserId(accessToken);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Response> responseList = wishService.getAllWishlistItems(accessToken, pageable);
        return responseList;
    }

    // 장바구니 상품 추가
    @PostMapping("/api/wishlist")
    @ResponseBody
    public Wishlist.Response addItem(@RequestHeader("Authorization") String accessToken, @RequestBody Wishlist.Request request){
//        long userId = wishlistService.findUserId(accessToken);

        Wishlist.Response item = wishService.addItemToWishlist(accessToken, request);
        return item;
    }
    // 장바구니 상품 삭제
    @DeleteMapping("/api/wishlist")
    @ResponseBody
    public Wishlist.Response deleteItem(@RequestHeader("Authorization") String accessToken, @RequestBody Wishlist.Request request){
//        long userId = wishlistService.findUserId(accessToken);

        Wishlist.Response item = wishService.deleteItemFromWishlist(accessToken, request);
        return item;
    }
}
