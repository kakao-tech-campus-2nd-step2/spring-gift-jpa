package gift.Controller;

import gift.Annotation.LoginMemberResolver;
import gift.Entity.Wishlist;
import gift.Model.MemberDto;
import gift.Model.ProductDto;
import gift.Model.WishlistDto;
import gift.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String getWishlist(@LoginMemberResolver MemberDto memberDto, Model model,
                                       @RequestParam(value="page", defaultValue="0") int page,
                                       @RequestParam(value="size", defaultValue="10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WishlistDto> paging = wishlistService.getWishlistByPage(memberDto, pageable);
        model.addAttribute("paging", paging);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paging.getTotalPages());
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
