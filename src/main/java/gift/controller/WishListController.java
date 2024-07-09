package gift.controller;

import gift.auth.LoginDTO;
import gift.auth.LoginUser;
import gift.service.WhishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WishListController 클래스는 위시리스트에 대한 RESTful API를 제공함
 */
@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WhishListService wishListService;

    @Autowired
    public WishListController(WhishListService wishListService) {
        this.wishListService = wishListService;
    }

    /**
     * 새로운 위시리스트 항목을 생성함
     *
     * @param productId 생성할 위시리스트 항목의 상품 ID
     * @param loginDTO     로그인된 사용자 정보
     * @return 생성된 위시리스트 항목의 ID 리스트
     */
    @PostMapping
    public ResponseEntity<List<Long>> createWishList(@RequestParam Long productId,
        @LoginUser LoginDTO loginDTO) {
        List<Long> newWishListIds = wishListService.createWishList(productId, loginDTO.getId());
        return ResponseEntity.ok(newWishListIds);
    }

    /**
     * 로그인된 사용자의 모든 위시리스트 항목을 조회함
     *
     * @param loginDTO 로그인된 사용자 정보
     * @return 지정된 사용자의 모든 위시리스트 항목의 상품 ID 리스트
     */
    @GetMapping
    public ResponseEntity<List<Long>> getWishListsByUserId(@LoginUser LoginDTO loginDTO) {
        List<Long> productIds = wishListService.getWishListsByUserId(loginDTO.getId());
        return ResponseEntity.ok(productIds);
    }

    /**
     * 로그인된 사용자의 모든 위시리스트 항목을 삭제함
     *
     * @param loginDTO 로그인된 사용자 정보
     * @return 204 No Content
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteWishListsByUserId(@LoginUser LoginDTO loginDTO) {
        if (wishListService.deleteWishListsByUserId(loginDTO.getId())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 로그인된 사용자가 지정된 상품을 위시리스트에서 삭제함
     *
     * @param productId 삭제할 상품의 ID
     * @param loginDTO     로그인된 사용자 정보
     * @return 204 No Content
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishListByUserIdAndProductId(@PathVariable Long productId,
        @LoginUser LoginDTO loginDTO) {
        wishListService.deleteWishListByUserIdAndProductId(loginDTO.getId(), productId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 로그인된 사용자가 지정된 상품을 위시리스트에서 추가함
     *
     * @param productId 추가할 상품의 ID
     * @param loginDTO 로그인된 사용자 정보
     * @return 200 OK
     */
    @PostMapping("/{productId}")
    public ResponseEntity<Void> addWishListByUserIdAndProductId(@PathVariable Long productId,
        @LoginUser LoginDTO loginDTO) {
        wishListService.addWishListByUserIdAndProductId(loginDTO.getId(), productId);
        return ResponseEntity.ok().build();
    }
}
