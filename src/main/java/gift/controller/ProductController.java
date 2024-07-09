package gift.controller;

import gift.service.ProductService;
import gift.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public String getAllProducts(Model model) {
        model.addAttribute("productList", productService.getAllProducts());
        return "index";
    }

    @GetMapping("/getProduct/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/postProduct")
    public String postProduct(@ModelAttribute Product product, Model model) {
        try{
            productService.postProduct(product);
            return "redirect:/api/getAllProducts";
        } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/getAllProducts";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product newProduct, Model model) {
        try{
            productService.updateProduct(id, newProduct);
            return "redirect:/api/getAllProducts";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}