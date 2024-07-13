package gift.controller;

import gift.exception.ProductException;
import gift.model.dto.ProductRequestDto;
import gift.model.dto.ProductResponseDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model,
        @RequestParam(required = false, defaultValue = "0", value = "pageNo") int pageNo,
        @RequestParam(required = false, defaultValue = "product", value = "criteria") String criteria) {
        Page<ProductResponseDto> productList = productService.getAllProducts(pageNo, criteria);
        model.addAttribute("productList", productList);
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequestDto());
        return "add-product-form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRequestDto productRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product-form";
        }
        productService.insertProduct(productRequestDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("productRequestDto",
            ProductRequestDto.from(productService.getProductById(id)));
        return "modify-product-form";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
        @Valid @ModelAttribute ProductRequestDto productRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "modify-product-form";
        }
        productService.updateProductById(id, productRequestDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    @ExceptionHandler(ProductException.class)
    public String handleProductException(ProductException productException, Model model) {
        model.addAttribute("errorMessage", productException.getMessage());
        return "error";
    }

}
