package gift.Controller;

import gift.Annotation.LoginMemberResolver;
import gift.Model.WishlistDto;
import gift.Model.MemberDto;
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
        Page<WishlistDto> paging = wishlistService.getWishlistByPage(memberDto.getId(), pageable);
        model.addAttribute("paging", paging);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paging.getTotalPages());
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addWishlistItem(@LoginMemberResolver MemberDto memberDto, @RequestBody WishlistDto wishListDto) {
        if(memberDto == null) {
            return "redirect:/login";
        }
        System.out.println("memberDto의 userId : " + memberDto.getId()); // 0이다!!!!!
        System.out.println("memberDto의 name : " + memberDto.getName());
        System.out.println("memberDto의 password : " + memberDto.getPassword());
        wishListDto.setUserId(memberDto.getId());
        wishlistService.addWishlistItem(wishListDto);
        return "redirect:/products";
    }

    @PostMapping("/wishlist/remove")
    public String removeWishlistItem(@LoginMemberResolver MemberDto memberDto, @RequestBody WishlistDto wishListDto) {
        if(memberDto == null) {
            return "redirect:/login";
        }

        Optional<WishlistDto> wishlistOptional = wishlistService.getWishlist(memberDto.getId()).stream()
                .filter(wishlist -> wishlist.getProductId() == wishListDto.getProductId())
                .findFirst();
        wishListDto.setUserId(memberDto.getId());

        wishlistService.removeWishlistItem(wishListDto, wishlistOptional.get());

        return "redirect:/wishlist";
    }
}
