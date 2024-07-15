package gift.controller;

import gift.domain.ProductDTO;
import gift.domain.WishListDTO;
import gift.service.WishListService;
import gift.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private JwtUtil jwtUtil;


    // 토큰에서 userId 추출
    private Long getUserId(String token) {
        return jwtUtil.extractUserId(token);

    }

    // 전체 위시리스트 조회
    @GetMapping
    public ResponseEntity<Page<WishListDTO>> getWishList(
        @RequestHeader("Authorization") String token,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Long userId = getUserId(token);
        Pageable pageable = PageRequest.of(page, size);
        Page<WishListDTO> wishList = wishListService.readWishList(userId, pageable);
        return ResponseEntity.ok(wishList);
    }


    // 위시리스트 추가
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addWishList(@RequestHeader("Authorization") String token,
       @RequestBody ProductDTO product) throws Exception {
        Long userId = getUserId(token);
        wishListService.addProductToWishList(userId, product);
        return new ResponseEntity<>("Product added to wishlist", HttpStatus.CREATED);
    }

    // 위시리스트 특정 상품 삭제
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> removeProductFromWishList(@RequestHeader("Authorization") String token,
        @PathVariable Long productId) {
        Long userId = getUserId(token);
        wishListService.removeProductFromWishList(userId, productId);
        return new ResponseEntity<>("Product removed from wishlist", HttpStatus.OK);
    }

    // 전체 위시리스트 삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> removeAllWishList(@RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        wishListService.removeWishList(userId);
        return new ResponseEntity<>("Wishlist deleted", HttpStatus.OK);
    }
}
