package gift.product.controller;

import gift.product.dto.AdminProductDto;
import gift.product.dto.JwtResponse;
import gift.product.dto.LoginMember;
import gift.product.dto.MemberDto;
import gift.product.model.Product;
import gift.product.service.AuthService;
import gift.product.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String REDIRECT_ADMIN_LOGIN_PROCESSING = "redirect:/admin/login/process";
    private final String REDIRECT_ADMIN_PRODUCTS = "redirect:/admin/products";
    private final ProductService productService;
    private final AuthService authService;

    public AdminController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "admin/loginForm";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto, RedirectAttributes redirectAttributes) {
        JwtResponse jwtResponse = authService.login(memberDto);
        redirectAttributes.addAttribute("accessToken", jwtResponse.token());

        return REDIRECT_ADMIN_LOGIN_PROCESSING;
    }

    @GetMapping("/login/process")
    public String loginProcess(@RequestParam("accessToken") String accessToken, Model model) {
        model.addAttribute("accessToken", accessToken);

        return "admin/loginProcess";
    }

    @PostMapping("login/process")
    @ResponseBody
    public ResponseEntity<Void> loginSuccess(@RequestParam("accessToken") JwtResponse jwtResponse,
        HttpServletResponse response) {
        addAccessTokenCookieInResponse(jwtResponse, response);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/products"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    @GetMapping("/products")
    public String products(HttpServletRequest request, Model model) {
        LoginMember loginMember = getLoginMember(request);
        List<Product> products = productService.getProductAll(loginMember);
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/products/insert")
    public String insertForm() {
        return "admin/insertForm";
    }

    @PostMapping("/products/insert")
    public String insertProduct(@Valid AdminProductDto adminProductDto,
        HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        productService.insertProduct(adminProductDto, loginMember);
        return REDIRECT_ADMIN_PRODUCTS;
    }

    @GetMapping("/products/update/{id}")
    public String updateForm(@PathVariable(name = "id") Long productId, Model model,
        HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        Product product = productService.getProduct(productId, loginMember);
        model.addAttribute("product", product);
        return "admin/updateForm";
    }

    @PutMapping("/products/update/{id}")
    public String updateProduct(@PathVariable(name = "id") Long productId,
        @Valid AdminProductDto adminProductDto, HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        productService.updateProduct(productId, adminProductDto, loginMember);
        return REDIRECT_ADMIN_PRODUCTS;
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long productId,
        HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        productService.deleteProduct(productId, loginMember);
        return REDIRECT_ADMIN_PRODUCTS;
    }

    private LoginMember getLoginMember(HttpServletRequest request) {
        return new LoginMember((Long) request.getAttribute("memberId"));
    }

    private void addAccessTokenCookieInResponse(JwtResponse jwtResponse,
        HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", jwtResponse.token());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
