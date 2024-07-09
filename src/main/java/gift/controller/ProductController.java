package gift.controller;

import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.findProducts());
        return "product-list";
    }

    @GetMapping("/api/products/{id}")
    public String getProduct(@PathVariable Long id, Model model){
        model.addAttribute("products", productService.findOne(id));
        return "product-list";
    }

    @GetMapping("/api/products/new")
    public String newProductForm(Model model){
        model.addAttribute("product", new ProductRequest());
        return "product-add-form";
    }

    @PostMapping("/api/products")
    public String addProduct(@Valid @ModelAttribute ProductRequest productRequest) {
        productService.register(productRequest);
        return "redirect:/api/products";
    }

    @GetMapping("/api/products/edit/{id}")
    public String editProductForm(@PathVariable long id, Model model){
        Product product = productService.findOne(id);
        model.addAttribute("product", product);
        return "product-edit-form";
    }
    @PostMapping("/api/products/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductRequest productRequest) {
        productService.update(id, productRequest);
        return "redirect:/api/products";
    }

    @GetMapping("/api/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/api/products";

    }
}
