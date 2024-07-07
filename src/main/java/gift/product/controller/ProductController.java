package gift.product.controller;

import gift.product.exception.ProductNotFoundException;
import gift.product.model.Product;
import gift.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null || text.trim().isEmpty() ? null : text);
            }
        });
    }

    @PostMapping
    public String createProduct(Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, Product product) {
        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
