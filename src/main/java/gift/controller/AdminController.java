package gift.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import gift.model.Product;
import gift.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String adminPage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "admin";
    }

    @GetMapping
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product(null, "", 0, ""));
        return "product-form";
    }

    @PostMapping
    public String addProduct(@ModelAttribute Product product, BindingResult bindingResult) {
        productService.createProduct(product, bindingResult);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return "redirect:/admin";
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @ModelAttribute Product updatedProduct, BindingResult bindingResult) {
        productService.updateProduct(id, updatedProduct, bindingResult);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
}
