package gift.controller;

import gift.dto.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductPageController {

    private final ProductService productService;

    public ProductPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String viewHomePage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = productService.getAllProducts(pageable);
        model.addAttribute("productPage", productPage);
        return "index";
    }

    @GetMapping("/new")
    public String createProductForm(Model model) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("product", productDTO);
        return "addProduct";
    }

    @PostMapping("/save")
    public String createProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addProduct";
        }
        productService.createProduct(productDTO);
        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable long id, Model model) {
        ProductDTO productDTO = productService.getProductById(id);
        model.addAttribute("product", productDTO);
        return "editProduct";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable long id, @Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editProduct";
        }
        productService.updateProduct(id, productDTO);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
