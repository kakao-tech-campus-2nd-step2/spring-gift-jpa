package gift;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("products", productService.getAllProducts());
        return "admin";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID는 null, 0, 음수는 불가입니다.");
        }
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product updatedProduct) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID는 null, 0, 음수는 불가입니다.");
        }
        productService.updateProduct(id, updatedProduct);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID는 null, 0, 음수는 불가입니다.");
        }
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
