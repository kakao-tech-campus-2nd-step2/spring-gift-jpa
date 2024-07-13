package gift.controller;

import gift.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProducts(Pageable pageable, Model model) {
        var products = productService.getProducts(pageable);
        model.addAttribute("data", "관리자");
        model.addAttribute("products", products);
        return "views/products";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        var product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "views/product";
    }

    @GetMapping("/products/add")
    public String getAddForm() {
        return "views/addProduct";
    }

    @GetMapping("/products/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model) {
        var product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "views/editProduct";
    }
}
