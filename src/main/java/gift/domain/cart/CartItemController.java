package gift.domain.cart;

import gift.domain.product.Product;
import gift.domain.user.dto.UserInfo;
import gift.global.resolver.LoginInfo;

import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class CartItemController {

    private final CartItemService cartItemService;
    private final JdbcTemplate jdbcTemplate;

    public CartItemController(CartItemService cartItemService, JdbcTemplate jdbcTemplate) {
        this.cartItemService = cartItemService;
        this.jdbcTemplate = jdbcTemplate;
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
     * 장바구니 조회
     */
    @GetMapping("/cart")
    public ResponseEntity<ResultResponseDto<List<Product>>> getProductsInCartByUserId(
        @LoginInfo UserInfo userInfo) {

        List<Product> products = cartItemService.getProductsInCartByUserId(userInfo.getId());

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
}
