package gift.ssr;

import gift.controller.member.MemberDto;
import gift.controller.product.ProductResponse;
import gift.controller.product.ProductRequest;
import gift.service.MemberService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final MemberService memberService;

    public AdminController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public String listMembers(Model model) {
        List<MemberDto> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/members/add")
    public String memberAddForm(Model model) {
        model.addAttribute("member", new MemberDto("", ""));
        return "member-add-form";
    }

    @PostMapping("/members/add")
    public String addMember(@ModelAttribute MemberDto member) {
        memberService.save(member);
        return "redirect:/admin/members";
    }

    @GetMapping("/members/edit/{email}")
    public String memberEditForm(@PathVariable String email, Model model) {
        model.addAttribute("member", memberService.find(email));
        return "member-edit-form";
    }

    @PostMapping("/members/edit/{email}")
    public String editMember(@PathVariable String email, @ModelAttribute MemberDto member) {
        memberService.update(email, member);
        return "redirect:/admin/members";
    }

    @GetMapping("/members/delete/{email}")
    public String deleteMember(@PathVariable String email) {
        memberService.delete(email);
        return "redirect:/admin/members";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/add")
    public String productAddForm(Model model) {
        model.addAttribute("product", new ProductRequest("", 0L, ""));
        return "product-add-form";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute @Valid ProductRequest product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String productEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.find(id));
        return "product-edit-form";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute @Valid ProductRequest product) {
        productService.update(id, product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
