package gift.controller;

import gift.dto.ProductRegisterRequestDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
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

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductRegisterRequestDto());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRegisterRequestDto productDto) {
        productService.addProduct(productDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID는 null, 0, 음수는 불가입니다.");
        }
        ProductRegisterRequestDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        model.addAttribute("productId", id);
        return "update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute ProductRegisterRequestDto productDto) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID는 null, 0, 음수는 불가입니다.");
        }
        productService.updateProduct(id, productDto);
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
