package gift.ssr;

import gift.controller.auth.LoginRequest;
import gift.controller.member.MemberRequest;
import gift.controller.member.MemberResponse;
import gift.controller.product.ProductRequest;
import gift.controller.product.ProductResponse;
import gift.service.MemberService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<MemberResponse> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/members/add")
    public String memberAddForm(Model model) {
        model.addAttribute("member", new LoginRequest("", ""));
        return "member-add-form";
    }

    @PostMapping("/members/add")
    public String addMember(@ModelAttribute LoginRequest member) {
        memberService.save(member);
        return "redirect:/admin/members";
    }

    @GetMapping("/members/edit/{id}")
    public String memberEditForm(@PathVariable UUID id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "member-edit-form";
    }

    @PostMapping("/members/edit/{id}")
    public String editMember(@PathVariable UUID id, @ModelAttribute MemberRequest member) {
        memberService.update(id, member);
        return "redirect:/admin/members";
    }

    @GetMapping("/members/delete/{email}")
    public String deleteMember(@PathVariable String email) {
        UUID id = memberService.findByEmail(email).id();
        memberService.delete(id);
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
    public String productEditForm(@PathVariable UUID id, Model model) {
        model.addAttribute("product", productService.find(id));
        return "product-edit-form";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable UUID id,
        @ModelAttribute @Valid ProductRequest product) {
        productService.update(id, product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable UUID id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}