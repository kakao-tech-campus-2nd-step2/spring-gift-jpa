package gift.controller;

import gift.common.dto.PageResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final ProductService productService;

    AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String registerProductForm() {
        return "addProduct";
    }

    @PostMapping("/admin/product")
    public String registerProduct(ProductRequest productRequest) {
        productService.register(productRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products")
    public String getAllProducts(Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageResponse response = productService.findAllProduct(page, size);
        model.addAttribute("products", response);
        return "productList";
    }

    @GetMapping("/admin/product/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        ProductResponse response = productService.findProduct(id);
        return response;
    }

    @GetMapping("/product")
    public String updateProductForm(@RequestParam("id") Long id) {
        return "editProduct";
    }

    @PutMapping("/admin/product/{id}")
    public String updateProduct(@PathVariable("id") Long id, ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/admin/product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
