package gift.controller;

import gift.annotation.LoginMember;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.model.WishRequest;
import gift.service.ProductService;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Wish> getWishes(@LoginMember Member member) {
        return wishService.getWishesByMemberId(member.getId());
    }

    @PostMapping
    public Wish addWish(@RequestBody WishRequest wishRequest, @LoginMember Member member) {
        // Add logic to find product by productId
        Product product = productService.findById(wishRequest.getProductId());
        return wishService.addWish(member, product);
    }

    @DeleteMapping("/{wishId}")
    public void deleteWish(@PathVariable Long wishId, @LoginMember Member member) {
        wishService.deleteWish(wishId);
    }
}