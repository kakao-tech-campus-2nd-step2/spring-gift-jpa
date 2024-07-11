
package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.service.ProductService;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<WishResponse> getWishes(@LoginMember Member member, Pageable pageable) {
        return wishService.getWishesByMemberId(member.getId(), pageable);
    }

    @PostMapping
    public Wish addWish(@RequestBody WishRequest wishRequest, @LoginMember Member member) {
        Product product = productService.findById(wishRequest.getProductId());
        return wishService.addWish(member, product);
    }

    @DeleteMapping("/{wishId}")
    public void deleteWish(@PathVariable Long wishId, @LoginMember Member member) {
        wishService.deleteWish(wishId);
    }
}