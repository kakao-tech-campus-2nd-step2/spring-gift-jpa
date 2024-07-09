package gift.Controller;

import gift.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import gift.Model.Product;
import gift.Model.RequestProduct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProduct(Model model) {
        List<Product> list = productService.getAllProducts();
        model.addAttribute("products", list);
        return "products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new RequestProduct("", 0, ""));
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(@Valid @ModelAttribute RequestProduct requestProduct) {
        productService.addProduct(requestProduct);
        return "redirect:/api/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.selectProduct(id);
        model.addAttribute("product", new RequestProduct(product.getName(), product.getPrice(), product.getImageUrl()));
        model.addAttribute("id", id);
        return "edit-product";
    }

    @PutMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute RequestProduct requestProduct) {
        productService.editProduct(id, requestProduct);
        return "redirect:/api/products";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }

}