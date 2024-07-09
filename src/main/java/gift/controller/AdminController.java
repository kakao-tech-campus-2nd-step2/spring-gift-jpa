package gift.controller;

import gift.dto.ProductDTO;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
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
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "product_list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO("", "0", ""));
        return "add_product_form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute @Valid ProductDTO productDTO, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productDTO", productDTO);
            return "add_product_form";
        }
        productService.saveProduct(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") long id, Model model) {
        Product product = productService.findProductsById(id);
        model.addAttribute("productDTO", ProductService.toDTO(product));
        model.addAttribute("productID", id);
        return "edit_product_form";
    }

    @PutMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id,
        @ModelAttribute @Valid ProductDTO updatedProductDTO,
        BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productID", id);
            return "edit_product_form";
        }
        productService.updateProduct(updatedProductDTO, id);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

}
