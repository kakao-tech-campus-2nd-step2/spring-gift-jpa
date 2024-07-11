package gift.controller;

import gift.auth.CheckRole;
import gift.auth.JwtService;
import gift.auth.LoginMember;
import gift.exception.wishlist.WishException;
import gift.model.Member;
import gift.model.Product;
import gift.request.JoinRequest;
import gift.request.LoginMemberDto;
import gift.request.LoginRequest;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.WishService;
import gift.utils.ScriptUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagingViewController {

    private final ProductService productService;
    private final MemberService memberService;
    private final WishService wishService;
    private final JwtService jwtService;

    public PagingViewController(ProductService productService, MemberService memberService,
        WishService wishService, JwtService jwtService) {
        this.productService = productService;
        this.memberService = memberService;
        this.wishService = wishService;
        this.jwtService = jwtService;
    }

    @GetMapping("/view/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member("", ""));
        return "joinForm";
    }

    @PostMapping("/view/join")
    public String join(@ModelAttribute("member") @Valid JoinRequest joinRequest,
        BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }

        Member joinedMember = memberService.join(joinRequest.email(), joinRequest.password());
        jwtService.createToken(joinedMember, response);
        return "redirect:/view/products";
    }

    @GetMapping("/view/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new Member("", ""));
        return "loginForm";
    }

    @PostMapping("/view/login")
    public String login(@ModelAttribute("member") @Valid LoginRequest loginRequest,
        BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "loginForm";
        }

        Member loginedMember = memberService.login(loginRequest.email(), loginRequest.password());
        jwtService.createTokenMVC(loginedMember, response);
        return "redirect:/view/products";
    }

    @GetMapping("/view/products")
    public String getAllProducts(Model model) {
        List<Product> productsList = productService.getAllProducts();
        model.addAttribute("products", productsList);
        return "pagingProducts";
    }

    @CheckRole("ROLE_USER")
    @GetMapping("/view/wish")
    public String getWishes(@LoginMember LoginMemberDto loginMemberDto, Model model) {
        List<Product> wishes = wishService.getMyWishList(loginMemberDto.id());
        model.addAttribute("products", wishes);
        return "wish";
    }

    @CheckRole("ROLE_USER")
    @PostMapping("/view/wish/add")
    public void addWish(@RequestParam("id") Long id, @LoginMember LoginMemberDto loginMemberDto
        , HttpServletResponse response) throws IOException {
        try {
            wishService.addMyWish(loginMemberDto.id(), id);
            ScriptUtils.alertAndMovePage(response, "정상적으로 추가되었습니다.", "/view/products");
        } catch (WishException e) {
            ScriptUtils.alertAndBackPage(response, e.getMessage());
        }
    }

    @CheckRole("ROLE_USER")
    @PostMapping("/view/wish/delete")
    public void deleteWish(@RequestParam("id") Long id, @LoginMember LoginMemberDto loginMemberDto,
        HttpServletResponse response) throws IOException {
        try {
            wishService.removeMyWish(loginMemberDto.id(), id);
            ScriptUtils.alertAndMovePage(response, "정상적으로 삭제되었습니다.", "/view/wish");
        } catch (WishException | IOException e) {
            ScriptUtils.alertAndBackPage(response, e.getMessage());
        }
    }


}
