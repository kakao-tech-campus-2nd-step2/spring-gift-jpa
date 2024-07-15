package gift.domain.cart;

import gift.domain.product.Product;
import gift.domain.user.dto.UserInfo;
import gift.global.resolver.LoginInfo;
import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService, JdbcTemplate jdbcTemplate) {
        this.cartItemService = cartItemService;
    }


    /**
     * 장바구니에 상품 담기
     */
    @PostMapping("/cart/{id}")
    public ResponseEntity<SimpleResultResponseDto> addCartItem(
        @PathVariable("id") Long productId, @LoginInfo UserInfo userInfo) {

        cartItemService.addCartItem(userInfo.getId(), productId);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "상품이 장바구니에 추가되었습니다.");
    }

    /**
     * 장바구니 조회 - 페이징(매개변수별)
     */
    @GetMapping(path = "/cart", params = "page")
    public ResponseEntity<ResultResponseDto<Page<Product>>> getProductsInCartByUserIdAndPageAndSort(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sort", defaultValue = "id_asc") String sort,
        @LoginInfo UserInfo userInfo) {
        int size = 10; // default
        Sort sortObj = getSortObject(sort);

        Page<Product> products = cartItemService.getProductsInCartByUserIdAndPageAndSort(
            userInfo.getId(),
            page,
            size,
            sortObj
        );

        return ResponseMaker.createResponse(HttpStatus.OK, "장바구니 조회에 성공했습니다.", products);
    }

    /**
     * 장바구니 상품 삭제
     */
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<SimpleResultResponseDto> deleteCartItem(
        @PathVariable("id") Long productId, @LoginInfo UserInfo userInfo) {

        cartItemService.deleteCartItem(userInfo.getId(), productId);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "장바구니에서 상품이 삭제되었습니다.");
    }

    private Sort getSortObject(String sort) {
        switch (sort) {
            case "price_asc":
                return Sort.by(Sort.Direction.ASC, "price");
            case "price_desc":
                return Sort.by(Sort.Direction.DESC, "price");
            case "name_asc":
                return Sort.by(Sort.Direction.ASC, "name");
            case "name_desc":
                return Sort.by(Sort.Direction.DESC, "name");
            default:
                return Sort.by(Sort.Direction.ASC, "id");
        }
    }
}
