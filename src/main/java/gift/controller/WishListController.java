package gift.controller;

import gift.dto.WishListDTO;
import gift.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public String viewWishList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction,
        HttpServletRequest request, Model model) {
        String email = (String) request.getAttribute("email");
        if (email == null) {
            return "redirect:/users/login";
        }

        Pageable pageable = wishListService.createPageRequest(page, size, sortBy, direction);
        Page<WishListDTO> wishListPage = wishListService.getWishListByUser(email, pageable);

        // 모델에 데이터 추가
        model.addAttribute("wishList", wishListPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", wishListPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "wishlist";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productId", new Long(0));
        return "add_product_to_wishlist";
    }

    @PostMapping("/add")
    public String addProductToWishList(@RequestBody Map<String, Long> payload, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        Long productId = payload.get("productId");
        wishListService.addProductToWishList(email, productId);
        return "redirect:/wishlist";
    }

    @DeleteMapping("/remove/{productId}")
    public String removeProductFromWishList(@PathVariable Long productId, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishListService.removeProductFromWishList(email, productId);
        return "redirect:/wishlist";
    }
}