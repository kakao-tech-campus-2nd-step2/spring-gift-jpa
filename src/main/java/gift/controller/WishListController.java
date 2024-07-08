package gift.controller;

import gift.DTO.ProductDTO;
import gift.DTO.Token;
import gift.DTO.UserDTO;
import gift.DTO.WishProductDTO;
import gift.security.AuthenticateMember;
import gift.service.ProductService;
import gift.service.UserService;
import gift.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WishListController {
    private final WishListService wishListService;
    private final ProductService productService;

    public WishListController(WishListService wishListService, ProductService productService){
        this.wishListService = wishListService;
        this.productService = productService;
    }
    /*
     * 위시리스트 내용 추가
     * email, productId를 받아 위시리스트에 상품을 추가
     * 성공 시 : 200, 성공
     * 실패 시 : Exception Handler에서 처리
     */
    @PostMapping("api/wishes/{productId}")
    public ResponseEntity<Void> addWishList(
            @PathVariable("productId") Long id, @AuthenticateMember UserDTO user
    ){
        WishProductDTO wishProductDTO = new WishProductDTO(user.getUserId(), id);
        wishListService.addWishList(wishProductDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*
     * 위시리스트 받아오기
     * 토큰을 기준으로 유저의 정보를 받아 해당 유저의 ID로 저장된 위시리스트를 반환
     * 성공 시 : 200, 위시리스트를 받아와 반환
     * 실패 시 : Exception Handler에서 처리
     */
    @GetMapping("api/wishes")
    public ResponseEntity<List<ProductDTO>> getWishList(@AuthenticateMember UserDTO user){
        List<WishProductDTO> wishList = wishListService.loadWishList(user.getUserId());

        List<ProductDTO> list = new ArrayList<>();
        for (WishProductDTO wishProduct : wishList) {
            list.add(productService.loadOneProduct(wishProduct.getProductId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /*
     * 위시리스트 내용 삭제
     * email, productId를 받아 위시리스트에 상품을 추가
     * 성공 시 : 200, 성공
     * 실패 시 : Exception Handler에서 처리
     */
    @DeleteMapping("api/wishes/{productId}")
    public ResponseEntity<Void> deleteWishProduct(
            @PathVariable("productId") Long id,
            @AuthenticateMember UserDTO user
    ){
        wishListService.deleteWishProduct(user.getUserId(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
