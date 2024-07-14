package gift.controller.page;

import gift.dto.product.ProductResponseDTO;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String admin(Model model) {
        List<ProductResponseDTO> products = productService.getAllProducts();

        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable long id, Model model) {
        ProductResponseDTO product = productService.getProduct(id);
        model.addAttribute("product", product);

        return "product_edit";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {
        return "product_add";
    }
}
