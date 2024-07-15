package gift.web;

import gift.service.product.ProductService;
import gift.web.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
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
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model, Pageable pageable) {
        model.addAttribute("products", productService.getProducts(pageable));
        return "products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        return "products";
    }

    @GetMapping("/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new ProductDto(1L, "name", 0L, "image.url"));
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        productService.createProduct(productDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute @Valid ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDto);
            model.addAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            return "edit";
        }
        productService.updateProduct(id, productDto);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
