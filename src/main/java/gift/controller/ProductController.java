package gift.controller;

import gift.service.ProductService;
import gift.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Product> productPage = productService.getAllProducts(page);
        model.addAttribute("productList", productPage.getContent());
        return "index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public String postProduct(@ModelAttribute Product product, Model model) {
        try{
            productService.saveProduct(product);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product newProduct, Model model) {
        try{
            productService.updateProduct(id, newProduct);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}