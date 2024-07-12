package gift.controller;

import gift.domain.Wish;
import gift.domain.Wish.wishDetail;
import gift.domain.Wish.wishSimple;
import gift.mapper.PageMapper;
import gift.service.WishService;
import gift.util.ParsingPram;
import gift.util.page.PageResult;
import gift.util.page.SingleResult;
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
        return new SingleResult(wishListService.getWish(id));
    }

    //    user id로 위시리스트 반환
    //    user id 검증
    @GetMapping
    public PageResult<wishSimple> getWishList(HttpServletRequest req,
        @Valid Wish.getList pram) {
        return PageMapper.toPageResult(wishListService.getWishList(parsingPram.getId(req), pram));
    }

    //  위시리스트 추가
    //  user id 검증, product id 검증,  위시 리스트내 중복여부 검증
    @PostMapping
    public SingleResult<Long> createWish(HttpServletRequest req,
        @Valid @RequestBody Wish.createWish create) {
        return new SingleResult(wishListService.createWish(parsingPram.getId(req), create));
    }

    //    wish id로 위시리스트 삭제
    //    wish id 검증
    @DeleteMapping("/{id}")
    public SingleResult<Long> deleteWish(@PathVariable long id) {
        return new SingleResult(wishListService.deleteWish(id));
    }

}
