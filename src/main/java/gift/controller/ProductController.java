package gift.controller;

import gift.dao.ProductDAO;
import gift.domain.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.*;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private static final String REDIRECT_URL = "redirect:/api/products";

    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public String getProducts(Model model) {
        List<Product> products = productDAO.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }

        productDAO.save(product);

        return REDIRECT_URL;  // 새로운 상품 추가 후 상품 조회 화면으로 리다이렉트
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productDAO.findById(id);

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
        productDAO.update(id, product);

        return REDIRECT_URL;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        // 요청받은 id를 가진 상품을 삭제
        productDAO.delete(id);

        return REDIRECT_URL;
    }

}
