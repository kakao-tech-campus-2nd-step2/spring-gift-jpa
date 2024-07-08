package gift.product.controller;

import gift.product.dto.LoginMember;
import gift.product.dto.WishDto;
import gift.product.model.Wish;
import gift.product.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/wishes")
public class AdminWishController {

    public static final String REDIRECT_ADMIN_WISHES = "redirect:/admin/wishes";
    private final WishService wishService;

    public AdminWishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public String wishes(HttpServletRequest request, Model model) {
        List<Wish> wishes = wishService.getWishAll(getLoginMember(request));
        model.addAttribute("wishes", wishes);
        return "admin/wishes";
    }

    @GetMapping("/insert")
    public String insertForm() {
        return "admin/insertWishForm";
    }

    @PostMapping("/insert")
    public String insertProduct(HttpServletRequest request, @Valid WishDto wishDto) {
        wishService.insertWish(wishDto, getLoginMember(request));
        return REDIRECT_ADMIN_WISHES;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long wishId,
        HttpServletRequest request) {
        wishService.deleteWish(wishId, getLoginMember(request));
        return REDIRECT_ADMIN_WISHES;
    }

    private LoginMember getLoginMember(HttpServletRequest request) {
        return new LoginMember((Long) request.getAttribute("id"));
    }

}
