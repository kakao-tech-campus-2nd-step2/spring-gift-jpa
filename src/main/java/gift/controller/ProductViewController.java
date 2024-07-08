package gift.controller;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductResponseDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductRequestDto("", 0, ""));
        return "product_form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductRequestDto productRequestDTO) {
        productService.addProduct(productRequestDTO);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getAllProducts().stream()
                .filter(p -> p.id.equals(id))
                .findFirst()
                .orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "product_edit_form";
        }
        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute ProductRequestDto productRequestDTO) {
        productService.updateProduct(id, productRequestDTO);
        return "redirect:/products";
    }
}
