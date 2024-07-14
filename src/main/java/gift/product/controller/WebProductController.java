package gift.product.controller;

import gift.product.dto.ProductRequest;
import gift.product.model.Product;
import gift.product.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebProductController {
    private final ProductService productService;

    public WebProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String viewHomepage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("productsList", products);
        return "index";
    }

    @GetMapping("/showNewProducts")
    public String showNewProducts(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProduct";
    }

    @PostMapping("/saveProducts")
    public String saveProducts(@ModelAttribute("product") ProductRequest productRequest, Model model) {
        Product product = Product.from(productRequest);
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/showUpdateProducts/{id}")
    public String showUpdateProducts(@PathVariable(value = "id") Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", ProductRequest.from(product.get()));
        return "updateProduct";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isEmpty()) {
            return "redirect:/products";
        }
        productService.deleteById(id);
        return "redirect:/products";
    }

}
