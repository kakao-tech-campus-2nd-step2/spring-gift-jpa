package gift.controller;

import gift.domain.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.*;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private static final String REDIRECT_URL = "redirect:/api/products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.getAllProducts(PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "productList";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }

        productService.addProduct(product);

        return REDIRECT_URL;  // 새로운 상품 추가 후 상품 조회 화면으로 리다이렉트
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);

        if (product == null) {
            return REDIRECT_URL;
        }

        model.addAttribute("product", product);

        return "editProduct";
    }

    @PutMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, @Valid @ModelAttribute Product product, BindingResult bindingResult) {
        // 상품 정보 수정
        if (bindingResult.hasErrors()) {
            return "editProduct";
        }
        productService.updateProduct(id, product);

        return REDIRECT_URL;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        // 요청받은 id를 가진 상품을 삭제
        productService.deleteProduct(id);

        return REDIRECT_URL;
    }

}
