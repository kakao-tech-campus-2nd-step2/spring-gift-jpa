package gift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gift.dto.ProductDto;
import gift.dto.response.ProductPageResponse;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping()
    public String getProducts(Model model, @RequestParam(value = "page", defaultValue = "0")int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        ProductPageResponse paging = productService.getPage(page, size);
        model.addAttribute("paging", paging);
        return "admin_page";
    }

    @GetMapping("/new")
    public String showProductForm(Model model){
        model.addAttribute("product", new Product("", 0, ""));
        return "product_form";
    }

    @PostMapping("/new")
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()){
            model.addAttribute("product", productDto);
            return "product_form";
        }

        productService.addProduct(productDto);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));       
        return "edit_product_form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()){
            model.addAttribute("product", productDto);
            return "product_form";
        }

        productService.updateProduct(productDto);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
}