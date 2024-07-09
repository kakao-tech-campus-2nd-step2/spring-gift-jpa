package gift.controller;

import gift.anotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    @ResponseBody
    public void addWish(@RequestBody Wish wish, @LoginMember Member member) {
        wishService.addWish(member.getId(), wish.getProductName());
    }

    @GetMapping
    public List<Wish> getWishes(@RequestParam Long memberId) {
        return wishService.getWishes(memberId);
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public void removeWish(@RequestBody Wish wish, @LoginMember Member member) {
        wishService.removeWish(member.getId(), wish.getProductName());
    }
}
