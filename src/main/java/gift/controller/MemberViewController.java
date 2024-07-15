package gift.controller;

import gift.dto.ProductDto;
import gift.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/members")
public class MemberViewController {

    private final MemberService memberService;

    public MemberViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원가입 폼을 반환
     */
    @GetMapping("/register")
    public String registerMemberForm() {
        return "memberRegister";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * 위시리스트 페이지를 반환
     *
     * @param model
     * @param pageable
     * @param httpServletRequest
     * @return view
     */
    @GetMapping("/wishlist")
    public String getWishlist(Model model, @PageableDefault(size = 5) Pageable pageable,
        HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        Page<ProductDto> wishlist = memberService.getAllWishlist(email, pageable);

        int totalPage = wishlist.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("wishlist", wishlist);

        return "wishlist";
    }
}
