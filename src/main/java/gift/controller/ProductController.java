package gift.controller;

import gift.dto.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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




@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String allProducts(Model model) {
        List<ProductDTO> productDTOs = productService.findAllProducts();
        model.addAttribute("products", productDTOs);
        return "Products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "Add_product";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Add_product";
        }
        productService.addProduct(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Optional<ProductDTO> productDTO = productService.findProductById(id);
        if (productDTO.isEmpty()) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", productDTO.get());
        return "Edit_product";
    }

    @PutMapping("/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Edit_product";
        }
        productService.updateProduct(productDTO);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}