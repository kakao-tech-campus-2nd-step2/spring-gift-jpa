package gift.controller;

import gift.DTO.ProductDTO;
import gift.auth.DTO.MemberDTO;
import gift.auth.LoginMember;
import gift.service.WishListService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WishListController 클래스는 위시리스트에 대한 RESTful API를 제공함
 */
@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    /**
     * 새로운 위시리스트 항목을 생성함
     *
     * @param productId 생성할 위시리스트 항목의 상품 ID
     * @param memberDTO 로그인된 사용자 정보
     * @return 생성된 위시리스트 항목의 ID 리스트
     */
    @PostMapping
    public ResponseEntity<List<ProductDTO>> createWishList(@RequestParam Long productId,
        @LoginMember MemberDTO memberDTO) {
        List<ProductDTO> newWishListIds = wishListService.addWishList(productId, memberDTO);
        return ResponseEntity.ok(newWishListIds);
    }

    /**
     * 로그인된 사용자의 모든 위시리스트 항목을 조회함
     *
     * @param memberDTO 로그인된 사용자 정보
     * @return 지정된 사용자의 모든 위시리스트 항목의 상품 ID 리스트
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getWishListsByUserId(@LoginMember MemberDTO memberDTO) {
        List<ProductDTO> productIds = wishListService.getWishListsByUserId(memberDTO.getId());
        return ResponseEntity.ok(productIds);
    }

    /**
     * 로그인된 사용자의 모든 위시리스트 항목을 삭제함
     *
     * @param memberDTO 로그인된 사용자 정보
     * @return 204 No Content
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteWishListsByUserId(@LoginMember MemberDTO memberDTO) {
        if (wishListService.deleteWishListsByUserId(memberDTO.getId())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 로그인된 사용자가 지정된 상품을 위시리스트에서 삭제함
     *
     * @param productId 삭제할 상품의 ID
     * @param memberDTO 로그인된 사용자 정보
     * @return 204 No Content
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishListByUserIdAndProductId(@PathVariable Long productId,
        @LoginMember MemberDTO memberDTO) {
        wishListService.deleteWishListByUserIdAndProductId(productId, memberDTO.getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * 로그인된 사용자가 지정된 상품을 위시리스트에서 추가함
     *
     * @param productId 추가할 상품의 ID
     * @param memberDTO 로그인된 사용자 정보
     * @return 200 OK
     */
    @PostMapping("/{productId}")
    public ResponseEntity<Void> addWishListByUserIdAndProductId(@PathVariable Long productId,
        @LoginMember MemberDTO memberDTO) {
        wishListService.addWishList(productId, memberDTO);
        return ResponseEntity.ok().build();
    }
}
