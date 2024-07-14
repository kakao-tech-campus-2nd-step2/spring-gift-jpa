package gift.controller;

import gift.exception.InvalidProductException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);
        model.addAttribute("productPage", productPage);
        model.addAttribute("product", new Product());
        return "product-list";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "product-list";
        }

        productService.addProduct(product);
        redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "product-list";
        }
        productService.updateProduct(product.getId(), product);
        redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        return "redirect:/products";
    }

    @GetMapping("/view/{id}")
    public String getProductDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product-detail"; // product-details.html 뷰 반환
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
            return "redirect:/products";
        }

    

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}