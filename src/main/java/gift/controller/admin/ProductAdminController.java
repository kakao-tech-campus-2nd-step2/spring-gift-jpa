package gift.controller.admin;

import gift.dto.request.ProductRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductAdminController {

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "version-SSR/index";
    }

    @GetMapping("/add")
    public String getAddForm(Model model) {
        model.addAttribute("product", new ProductRequest()); // Add an empty Product object for the form
        return "version-SSR/add-form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductRequest product) {
        try {
            productService.addProduct(product.getName(), product.getPrice(), product.getImageUrl());
            return "redirect:/";
        } catch (Exception e) {
            return "version-SSR/add-error";
        }
    }

    @PostMapping("/deleteSelected")
    public String deleteSelectedProduct(@RequestParam("productIds") List<Long> productIds) {
        productService.deleteProducts(productIds);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.getProduct(id)); // Add an empty Product object for the form
        return "version-SSR/edit-form";
    }

    @PostMapping("/edit")
    public String editProduct(@Valid ProductRequest product) {
        try {
            productService.updateProduct(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
            return "redirect:/";
        } catch (Exception e) {
            return "version-SSR/edit-error";
        }
    }

}
