package gift.controller;

import gift.domain.Product;
import gift.dto.ProductRegisterRequestDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {
    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getPagedProducts(Model model,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> productPage = productService.getPagedProducts(pageable);
        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
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
