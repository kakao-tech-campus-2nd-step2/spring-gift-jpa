package gift.Controller;

import gift.Model.Member;
import gift.Model.Product;
import gift.Service.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @GetMapping("/api/products/add")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "post";
    }

    @PostMapping("/api/products")
    public String createProduct(@Valid @ModelAttribute Product product) {

        productService.addProduct(product);
        return "redirect:/api/products";
    }

    @GetMapping("/api/products/update/{id}")
    public String editProductForm(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "update";
    }

    @PostMapping("/api/products/update/{id}")
    public String updateProduct(@PathVariable(value = "id") Long id, @Valid @ModelAttribute Product newProduct) {
        productService.updateProduct(newProduct);
        return "redirect:/api/products";
    }

    @PostMapping("/api/products/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }
}
