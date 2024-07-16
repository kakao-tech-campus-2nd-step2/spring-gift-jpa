package gift.wishlist.controller;

import gift.product.model.Product;
import gift.wishlist.service.WishListService;
import gift.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 회원 id로 위시리스트 연결하려면
    @GetMapping("/{member_id}")
    public WishList getWishList(@PathVariable Long member_id) {
        return wishListService.findByMemberId(member_id);
    }

    /** 페이지네이션된 위시리스트 데이터를 반환
     * 특정 페이지와 크기 요청: /wishlist/{member_id}/paged?page=1&size=5
     * 페이지 번호: 0
     * 페이지 크기: 10
     * 정렬: 이름을 기준으로 오름차순 정렬 (기본값) **/
    @GetMapping("/{member_id}/paged")
    public Page<WishList> getWishListPaged(
            @PathVariable Long member_id,
            @RequestParam(defaultValue = "0") int page, // 요청 파라미터 page를 받아 페이지 번호를 설정
            @RequestParam(defaultValue = "10") int size, // 요청 파라미터 size를 받아 페이지 크기를 설정
            @RequestParam(defaultValue = "name,asc") String[] sort) // 요청 파라미터 sort를 받아 정렬 기준과 방향을 설정
    {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]); //  sort 배열의 두 번째 요소를 Sort.Direction 객체로 변환
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0])); // Pageable 객체를 생성

        return wishListService.findByMemberId(member_id, pageable); // 페이지네이션된 위시리스트 데이터를 반환
    }

    // 해당 위시리스트에 상품 추가 연결
    @PostMapping("/{member_id}/add")
    public void addProductToWishList(@PathVariable Long member_id, @RequestBody Product product) {
        wishListService.addProductToWishList(member_id, product);
    }

    // 해당 위시리스트에 상품 삭제 연결
    @DeleteMapping("/{member_id}/remove/{productId}")
    public void removeProductFromWishList(@PathVariable Long member_id, @PathVariable Long productId) {
        wishListService.removeProductFromWishList(member_id, productId);
    }
}