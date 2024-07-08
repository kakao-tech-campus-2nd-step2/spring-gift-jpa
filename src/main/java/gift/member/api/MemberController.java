package gift.member.api;

import gift.wishlist.api.WishesController;
import gift.member.validator.LoginMember;
import gift.member.application.MemberService;
import gift.auth.dto.AuthResponse;
import gift.member.dto.MemberDto;
import gift.product.api.ProductController;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final ProductController productController;
    private final WishesController wishesController;

    public MemberController(MemberService memberService,
                            ProductController productController,
                            WishesController wishesController) {
        this.memberService = memberService;
        this.productController = productController;
        this.wishesController = wishesController;
    }

    @GetMapping("/register")
    public String showSignupView() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public void signUp(@RequestBody @Valid MemberDto memberDto) {
        memberService.registerMember(memberDto);
    }

    @GetMapping("/login")
    public String showLoginView() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AuthResponse login(@RequestBody @Valid MemberDto memberDto) {
        String token = memberService.authenticate(memberDto);
        return AuthResponse.of(token);
    }

    @GetMapping("/wishlist")
    public String showWishlistView(@LoginMember Long memberId, Model model) {
        model.addAttribute("productList", productController.getAllProducts());
        model.addAttribute("wishlist", wishesController.getAllWishes(memberId));
        return "wishlist";
    }

}
