package gift.controller.admin;

import gift.controller.dto.request.ProductRequest;
import gift.controller.dto.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String getProducts(Model model,
              @PageableDefault(size = 10) Pageable pageable) {
        Page<ProductResponse> products = productService.findAllProductPaging(pageable);
        model.addAttribute("products", products);
        return "product/products";
    }

    @GetMapping("/new")
    public String newProduct() {
        return "product/newProduct";
    }

    @GetMapping("/{id}")
    public String updateProduct(@PathVariable("id") @NotNull @Min(1) Long id, Model model) {
        ProductResponse product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/editProduct";
    }

    @PostMapping("")
    public String newProduct(@ModelAttribute ProductRequest request) {
        productService.save(request);
        return "redirect:/admin/product";
    }

    @PutMapping("/{id}")
    public String updateProduct(
            @PathVariable("id") @NotNull @Min(1) Long id,
            @ModelAttribute ProductRequest request) {
        productService.updateById(id, request);
        return "redirect:/admin/product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") @NotNull @Min(1) Long id) {
        productService.deleteById(id);
        return "redirect:/admin/product";
    }
}
