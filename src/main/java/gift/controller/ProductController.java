package gift.controller;

import gift.exception.InvalidProductException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        return "product-list";
    }

    @PostMapping
    public String addProduct(@ModelAttribute @Valid Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.addProduct(product);
        } catch (InvalidProductException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid product: " + e.getMessage());
        }

        return "redirect:/products";
    }

    @PostMapping("/{id}")
    public String updateProduct(@Valid @PathVariable Long id, @ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.updateProduct(id, product);
        } catch (InvalidProductException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid product: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/view/{id}")
    public String getProductDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found" );
            return "redirect:/products";
        }
        return "product-detail";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable("id") Long id) {
        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Product not found: " + e.getMessage());
        }
    }
}