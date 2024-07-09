package gift.controller;

import gift.ProductConverter;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String allProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        List<ProductDTO> productDTOs = products.stream()
            .map(ProductConverter::convertToDTO)
            .collect(Collectors.toList());
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
        Product product = ProductConverter.convertToEntity(productDTO);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        ProductDTO productDTO = ProductConverter.convertToDTO(product);
        model.addAttribute("product", productDTO);
        return "Edit_product";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Edit_product";
        }
        Product product = ProductConverter.convertToEntity(productDTO);
        productService.updateProduct(id, product);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}