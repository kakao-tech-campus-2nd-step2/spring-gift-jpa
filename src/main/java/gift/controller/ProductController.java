package gift.controller;

import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductsForm(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "create_product";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping
    public String createProduct(Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, Product product) {
        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
