package gift.product.controller;

import gift.common.exception.ProductNotFoundException;
import gift.product.model.Product;
import gift.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ShowPageController {
    private final ProductService productService;

    public ShowPageController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public String showProductsForm(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "crudPage/create_product";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        model.addAttribute("product", product);
        return "crudPage/edit_product";
    }
}
