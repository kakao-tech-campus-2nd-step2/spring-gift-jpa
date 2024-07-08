package gift.controller;

import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> productList = new ArrayList<>(productService.getAllProducts());
        model.addAttribute("products", productList);
        return "product-list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        productService.getProductById(id);
        model.addAttribute("product", productService.getProductById(id));
        return "product-form";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute Product product,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.getProductById(id);
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
