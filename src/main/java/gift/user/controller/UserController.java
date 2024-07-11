package gift.user.controller;

import gift.product.dto.ProductResponseDto;
import gift.wishlist.dto.WishListResponseDto;
import gift.global.annotation.Products;
import gift.wishlist.service.WishListService;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

// login한 user에게 보여줄 view를 반환하는 Controller
@Controller
@RequestMapping("/users/{user-id}")
public class UserController {

    private final WishListService wishListService;

    public UserController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 로그인 직후에 메인 화면(wishlist)을 보여주는 핸들러
    @GetMapping("/main")
    public String loadUserMainPage(@PathVariable(name = "user-id") long userId,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
        Model model) {
        // 특정 id를 갖는 사람이 추가한 위시 리스트 전체를 가져와서 thymeleaf를 통해 html로 전송
        List<WishListResponseDto> wishListResponseDtoList = wishListService.readWishProducts(
            userId);

        model.addAttribute("userId", userId);
        model.addAttribute("products", wishListResponseDtoList);
        model.addAttribute("token", token);
        return "html/user-main";
    }

    // 모든 제품을 추가하는 화면을 보여주는 핸들러
    // 모든 제품을 가져오는 행위는 resolver로 처리가 가능해서 resolver를 사용하였습니다.
    @GetMapping("/all-products")
    public String loadAddingPage(@PathVariable(name = "user-id") long userId,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
        @Products List<ProductResponseDto> products, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("products", products);
        model.addAttribute("token", token);
        return "html/user-all-products";
    }
}
