package gift.Controller;

import gift.Annotation.LoginMemberResolver;
import gift.Entity.Wishlist;
import gift.Model.MemberDto;
import gift.Model.WishlistDto;
import gift.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String getWishlist(@LoginMemberResolver MemberDto memberDto, Model model) {
        List<Wishlist> wishlist = wishlistService.getWishlist(memberDto.getId());
        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addWishlistItem(@LoginMemberResolver MemberDto memberDto, @RequestBody WishlistDto wishlistDto) {
        if(memberDto == null) {
            return "redirect:/login";
        }
        wishlistDto.setUserId(memberDto.getId());
        wishlistService.addWishlistItem(wishlistDto);
        return "redirect:/products";
    }

    @PostMapping("/wishlist/remove")
    public String removeWishlistItem(@LoginMemberResolver MemberDto memberDto, @RequestBody WishlistDto wishlistDto) {
        if(memberDto == null) {
            return "redirect:/login";
        }

        Optional<Wishlist> wishlistOptional = wishlistService.getWishlist(memberDto.getId()).stream()
                .filter(wishlist -> wishlist.getProductId() == wishlistDto.getProductId())
                .findFirst();
        wishlistDto.setUserId(memberDto.getId());

        wishlistService.removeWishlistItem(wishlistDto, wishlistOptional.get());

        return "redirect:/wishlist";
    }
}
