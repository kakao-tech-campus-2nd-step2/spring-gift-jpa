package gift.Controller;

import gift.Exception.ProductNotFoundException;
import gift.Model.Product;

import java.util.List;
import java.util.Optional;

import gift.Service.ProductService;
import gift.Valid.NameValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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
        List<Product> products = productService.getAll(token);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model){
        model.addAttribute("product", new Product());
        return "add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "add";
        }
        productService.add(token, product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model){
        Optional<Product> productOptional = productService.getById(token, id);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        Product product = productOptional.get();
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute @Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "edit";
        }
        productService.edit(token, id, product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.delete(token, id);
        return "redirect:/admin/products";
    }
}
