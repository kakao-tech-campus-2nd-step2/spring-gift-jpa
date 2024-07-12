package gift.Controller;

import gift.Model.DTO.ProductDTO;

import java.util.List;

import gift.Service.ProductService;
import gift.Valid.NameValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminController {
    private NameValidator nameValidator;
    private final ProductService productService;
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiJ9.M5YfW43tAR9_HEvIj-1Wgvkc9b_Cg23TZgDRNBoPqdU";

    public AdminController(ProductService productService){
        this.productService = productService;
    }

    @Autowired
    public void setNameValidator(NameValidator nameValidator){this.nameValidator = nameValidator;}

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @GetMapping
    public String getAllProducts(Model model){
        List<ProductDTO> products = productService.getAll(token);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model){
        model.addAttribute("product", new ProductDTO(0L, "a",0,"b"));
        return "add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "add";
        }
        productService.add(token, productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model){
        ProductDTO productDTO = productService.getById(token, id);

        model.addAttribute("product", productDTO);
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "edit";
        }
        productService.edit(token, id, productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.delete(token, id);
        return "redirect:/admin/products";
    }
}
