package gift.controller;

import gift.dto.MemberDto;
import gift.dto.WishDto;
import gift.model.member.LoginMember;
import gift.model.wish.Wish;
import gift.service.WishListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public String getAllWishes(@LoginMember MemberDto memberDto,@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Wish> wishPage = wishListService.getAllWishes(PageRequest.of(page, 20));
        model.addAttribute("wishes", wishPage.getContent());
        model.addAttribute("totalPages", wishPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "manageWishList";
    }

    @PostMapping
    public ResponseEntity<Void> insertWish(@LoginMember MemberDto memberDto, @RequestBody WishDto wishDto) {
        wishListService.insertWish(wishDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@LoginMember MemberDto memberDto, @PathVariable Long id) {
        wishListService.deleteWish(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWish(@LoginMember MemberDto memberDto, @PathVariable Long id, @RequestBody WishDto wishDto) {
        wishListService.updateWish(id,wishDto);
        return ResponseEntity.ok().build();
    }
}