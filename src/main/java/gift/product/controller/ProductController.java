package gift.product.controller;

import gift.product.model.Product;
import gift.product.service.ProductService;
import org.springframework.data.domain.Page;
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
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping
    @ResponseBody
    public Page<Product> getProductsByPage(@RequestParam int page,
                                           @RequestParam int size,
                                           @RequestParam(defaultValue = "price") String sortBy,
                                           @RequestParam(defaultValue = "desc") String direction) {
        return productService.getProductsByPage(page, size, sortBy, direction);
    }
}
