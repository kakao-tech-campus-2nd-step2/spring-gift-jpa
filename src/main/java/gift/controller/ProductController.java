package gift.controller;

import gift.exceptions.CustomException;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import gift.dto.*;


@Controller
@Validated
@RequestMapping("/v3/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts(Model model, @PageableDefault(size = 3) Pageable pageable) {
        ProductsPageResponseDTO products = productService.getAllProducts(pageable);
        model.addAttribute("products", products);
        return "manage";
    }

    @PostMapping
    public String addProduct(@Valid  @RequestBody ProductRequestDTO productRequestDTO) {
        validateProductName(productRequestDTO.name());
        productService.createProduct(productRequestDTO);

        return "redirect:/v3/products";
    }

    @PostMapping("/{id}")
    public String modifyProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        validateProductName(productRequestDTO.name());
        productService.updateProduct(id, productRequestDTO);

        return "redirect:/v3/products";
    }

    @DeleteMapping("/{id}")
    public String DeleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return "redirect:/v3/products";
    }

    private void validateProductName(String name) {
        if (name.contains("카카오")) {
            throw CustomException.invalidNameException();
        }
    }
}