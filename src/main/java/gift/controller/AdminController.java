package gift.controller;

import gift.dto.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/admin/products")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDTO(null, "", 0, ""));
        return "product_form";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", productDTO);
            model.addAttribute("org.springframework.validation.BindingResult.product", result); // 명시적 추가
            return "product_form";
        }
        productService.addProduct(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        ProductDTO productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        return "product_edit";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute ProductDTO productDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", productDTO);
            model.addAttribute("org.springframework.validation.BindingResult.product", result);
            return "product_edit";
        }
        productService.updateProduct(id, productDTO);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
