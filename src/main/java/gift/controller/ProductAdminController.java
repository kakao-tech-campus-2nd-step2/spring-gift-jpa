package gift.controller;

import gift.dto.ProductRequest;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String getAllProducts(Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> productPage = productService.getAllProducts(pageable);
        model.addAttribute("productPage", productPage);
        model.addAttribute("sortBy", sortBy);
        return "product-list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest());
        return "product-form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("productRequest") ProductRequest productRequest,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        productService.saveProduct(productRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        ProductRequest productRequest = new ProductRequest(
            product.getName(), product.getPrice(), product.getImg());
        model.addAttribute("productRequest", productRequest);
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
        @Valid @ModelAttribute("productRequest") ProductRequest productRequest,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "product-form";
        }
        productService.updateProduct(id, productRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
