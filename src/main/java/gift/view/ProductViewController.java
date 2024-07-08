package gift.view;

import gift.controller.ProductController;
import gift.model.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class ProductViewController
{
    private final ProductController productController;

    public ProductViewController(ProductController productController) {
        this.productController = productController;
    }

    @GetMapping("")
    public String getAllProducts(Model model) {
        List<Product> products = productController.getAllProducts().getBody();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Product product = productController.findById(id).getBody();
        model.addAttribute("product", product);
        return "product_detail"; // 적절한 Thymeleaf 템플릿이 있는지 확인
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product(0, "", 0, ""));
        return "add_product";
    }

    @PostMapping("")
    public String createProduct(@ModelAttribute Product product) {
        productController.createProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productController.findById(id).getBody();
        model.addAttribute("product", product);
        return "add_product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productController.updateProduct(id, product);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productController.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
