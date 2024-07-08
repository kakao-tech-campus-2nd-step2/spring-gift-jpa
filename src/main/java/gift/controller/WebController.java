package gift.controller;

import gift.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {



    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/user-wishes")
    public String showWishesPage() {
        return "user-wishes";
    }

    @GetMapping("/user-products")
    public String showUserProductsPage(Model model) {
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return "error/500";
        }
        model.addAttribute("products", response.getBody());
        return "user-products";
    }

    @Autowired
    private ProductController productController;

    @GetMapping("/products")
    public String viewProductPage(Model model) {
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return "error/500";
        }
        model.addAttribute("products", response.getBody());
        return "product/index";
    }

    @GetMapping("/products/new")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/new";
    }

    @PostMapping("/products")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    model.addAttribute("valid_" + error.getField(), error.getDefaultMessage())
            );
            return "product/new";
        }
        ResponseEntity<Object> response = productController.addProduct(product, bindingResult);
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            Map<String, String> errors = (Map<String, String>) response.getBody();
            errors.forEach((key, value) -> model.addAttribute("valid_" + key, value));
            return "product/new";
        }
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        ResponseEntity<Product> response = productController.getProductById(id);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return "error/500";
        }
        model.addAttribute("product", response.getBody());
        return "product/edit";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    model.addAttribute("valid_" + error.getField(), error.getDefaultMessage())
            );
            return "product/edit";
        }
        ResponseEntity<Object> response = productController.updateProduct(id, product, bindingResult);
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            Map<String, String> errors = (Map<String, String>) response.getBody();
            errors.forEach((key, value) -> model.addAttribute("valid_" + key, value));
            return "product/edit";
        }
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productController.deleteProduct(id);
        return "redirect:/products";
    }
}