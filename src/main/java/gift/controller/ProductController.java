package gift.controller;

import gift.dto.ProductDto;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> productPage = productService.getProducts(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("sortField", pageable.getSort().iterator().next().getProperty());
        model.addAttribute("sortDirection", pageable.getSort().iterator().next().getDirection().toString());
        return "index";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productService.addProduct(product);
        return "redirect:/api/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute @Valid ProductDto productDto) {
        Product updatedProduct = new Product(id, productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productService.updateProduct(id, updatedProduct);
        return "redirect:/api/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }
}
