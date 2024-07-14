package gift.controller;

import gift.DTO.Product.ProductRequest;
import gift.DTO.Product.ProductResponse;
import gift.DTO.User.UserRequest;
import gift.DTO.User.UserResponse;
import gift.DTO.Wish.WishProductRequest;
import gift.DTO.Wish.WishProductResponse;
import gift.security.AuthenticateMember;
import gift.service.ProductService;
import gift.service.UserService;
import gift.service.WishListService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishListController {
    private final WishListService wishListService;
    private final ProductService ps;

    public WishListController(WishListService wishListService, ProductService ps){
        this.wishListService = wishListService;
        this.ps = ps;
    }
    /*
     * 위시리스트 내용 추가
     * userId, productId를 받아 위시리스트에 상품을 추가
     * 성공 시 : 200, 성공
     * 실패 시 : Exception Handler에서 처리
     */
    @PostMapping("api/wishes/{productId}")
    public ResponseEntity<Void> addWishList(
            @PathVariable("productId") Long id, @AuthenticateMember UserResponse userRes
    ){
        ProductResponse productRes = ps.readOneProduct(id);

        WishProductRequest wishProduct = new WishProductRequest(userRes, productRes);
        wishListService.addWishList(wishProduct);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*
     * 위시리스트 받아오기
     * 토큰을 기준으로 유저의 정보를 받아 해당 유저의 ID로 저장된 위시리스트를 반환
     * 성공 시 : 200, 위시리스트를 받아와 반환
     * 실패 시 : Exception Handler에서 처리
     */
    @GetMapping("api/wishes")
    public ResponseEntity<Page<WishProductResponse>> getWishList(
            @AuthenticateMember UserResponse user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){
        Page<WishProductResponse> wishList = wishListService.loadWishList(user.getId(), page, size);

        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }
    /*
     * 위시리스트 수량 수정하기
     */
    @PutMapping("api/wishes/{productId}")
    public ResponseEntity<Void> updateWishProduct(
            @PathVariable("productId") Long productId,
            @AuthenticateMember UserResponse user,
            @RequestParam int count
    ){
        Long id = user.getId();
        wishListService.updateWishProduct(id, productId, count);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*
     * 위시리스트 내용 삭제
     * email, productId를 받아 위시리스트에 상품을 추가
     * 성공 시 : 200, 성공
     * 실패 시 : Exception Handler에서 처리
     */
    @DeleteMapping("api/wishes/{productId}")
    public ResponseEntity<Void> deleteWishProduct(
            @PathVariable("productId") Long productId,
            @AuthenticateMember UserResponse user
    ){
        Long id = user.getId();
        wishListService.deleteWishProduct(id, productId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
