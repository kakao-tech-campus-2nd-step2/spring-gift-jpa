package gift.controller;

import gift.dto.WishlistDTO;
import gift.service.WishlistService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String getWishlist(Principal principal, Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "productName") String sort,
        @RequestParam(defaultValue = "asc") String direction) {

        String username = principal.getName();
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<WishlistDTO> wishlistPage = wishlistService.getWishlistByUser1(username, pageable);

        model.addAttribute("wishlistPage", wishlistPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        return "wishlist";
    }


    @PostMapping("/add")
    @ResponseBody
    public String addToWishlist(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, Principal principal) {
        if (principal == null) {
            return "회원만 찜할 수 있습니다.";
        }
        String username = principal.getName();
        wishlistService.addToWishlist(username, productId, quantity);
        return "상품이 위시리스트에 추가되었습니다.";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public String updateQuantity(@PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        wishlistService.updateQuantity(id, quantity);
        return "수량이 변경되었습니다.";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String removeFromWishlist(@PathVariable("id") Long id) {
        wishlistService.removeFromWishlist(id);
        return "상품이 위시리스트에서 삭제되었습니다.";
    }
}
