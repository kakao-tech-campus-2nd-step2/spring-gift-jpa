package gift.controller;


import gift.dto.CreateProductDto;
import gift.dto.ProductDto;
import gift.dto.UpdateProductDto;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products/manager")
public class ProductManagerController {
    private final ProductService productService;

    public ProductManagerController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productManager(Model model) {
        List<ProductDto> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "products-manager";
    }

    @PostMapping
    public String addProduct(@ModelAttribute CreateProductDto createProductDto) {
        productService.createProduct(createProductDto);
        return "redirect:/products/manager";
    }

    @PutMapping
    public String updateProduct(@ModelAttribute UpdateProductDto updateProductDto) {
        productService.updateProduct(updateProductDto);
        return "redirect:/products/manager";
    }
}
