package gift.controller;

import gift.DTO.UserDTO;
import gift.DTO.WishProductDTO;
import gift.security.AuthenticateMember;
import gift.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishListController {
    private final WishListService wishListService;


    public WishListController(WishListService wishListService){
        this.wishListService = wishListService;
    }
    /*
     * 위시리스트 내용 추가
     * userId, productId를 받아 위시리스트에 상품을 추가
     * 성공 시 : 200, 성공
     * 실패 시 : Exception Handler에서 처리
     */
    @PostMapping("api/wishes/{productId}")
    public ResponseEntity<Void> addWishList(
            @PathVariable("productId") Long id, @AuthenticateMember UserDTO user
    ){
        WishProductDTO wishProductDTO = new WishProductDTO(user.getUserId(), id, 1);
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
    public ResponseEntity<List<WishProductDTO>> getWishList(@AuthenticateMember UserDTO user){
        List<WishProductDTO> wishList = wishListService.loadWishList(user.getUserId());

        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }
    /*
     * 위시리스트 수량 수정하기
     */
    @PutMapping("api/wishes/{productId}")
    public ResponseEntity<Void> updateWishProduct(
            @PathVariable("productId") Long productId,
            @AuthenticateMember UserDTO user,
            @RequestParam int count
    ){
        wishListService.updateWishProduct(user.getUserId(), productId, count);

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
            @AuthenticateMember UserDTO user
    ){
        wishListService.deleteWishProduct(user.getUserId(), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
