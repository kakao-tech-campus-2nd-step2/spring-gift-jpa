package gift.controller;

import gift.dto.ProductDto;
import gift.service.ProductService;
import gift.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return productService.getProducts(page, size);
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-product";
        }
        if (productDto.getName().contains("카카오")) {
            result.rejectValue("name", "error.product", "상품 이름에 '카카오'가 포함되어 있습니다. 담당 MD와 협의하십시오.");
            return "add-product";
        }
        productService.addProduct(productDto);
        return "redirect:/view/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/view/admin/products";
        }
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-product";
        }
        if (productDto.getName().contains("카카오")) {
            result.rejectValue("name", "error.product", "상품 이름에 '카카오'가 포함되어 있습니다. 담당 MD와 협의하십시오.");
            return "edit-product";
        }
        productService.updateProduct(id, productDto);
        return "redirect:/view/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/view/admin/products";
    }
}
