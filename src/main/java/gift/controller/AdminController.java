package gift.controller;

import gift.domain.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/admin/products")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin";
    }

    @GetMapping("/add")
    public String addProduct() {
        return "add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String delProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "edit";
    }

    @PutMapping( "edit/{id}")
    public String updateProduct(@PathVariable int id, @Valid @ModelAttribute("product") Product product) {
        productService.updateProduct(id, product);
        return "redirect:/admin/products";
    }
}