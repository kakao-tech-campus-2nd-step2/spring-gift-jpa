package gift.controller;

import gift.domain.Wish;
import gift.domain.Wish.wishDetail;
import gift.domain.Wish.wishSimple;
import gift.errorException.ListResult;
import gift.errorException.SingleResult;
import gift.service.WishService;
import gift.util.ParsingPram;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wish")
public class WishController {

    @Autowired
    private WishService wishListService;
    @Autowired
    private ParsingPram parsingPram;

    //    Wish id로 상세정보 반환
    //    wish id 검증
    @GetMapping("/{id}")
    public SingleResult<wishDetail> getWish(@PathVariable long id) {
        System.out.println(wishListService.getWish(id));
        return new SingleResult(wishListService.getWish(id));
    }

    //    user id로 위시리스트 반환
    //    user id 검증
    @GetMapping
    public ListResult<wishSimple> getWishList(HttpServletRequest req) {
        return new ListResult(wishListService.getWishList(parsingPram.getId(req)));
    }

    //  위시리스트 추가
    //  user id 검증, product id 검증,  위시 리스트내 중복여부 검증
    @PostMapping
    public SingleResult<Integer> createWish(HttpServletRequest req, @Valid @RequestBody Wish.createWish create) {
        return new SingleResult(wishListService.createWish(parsingPram.getId(req),create));
    }

    //    wish id로 위시리스트 삭제
    //    wish id 검증
    @DeleteMapping("/{id}")
    public SingleResult<Integer> deleteWish(@PathVariable long id) {
        return new SingleResult(wishListService.deleteWish(id));
    }

}
