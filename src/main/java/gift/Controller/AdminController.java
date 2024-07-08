package gift.Controller;

import gift.Model.Product;
import gift.Model.ProductDAO;
import java.util.List;

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
    private ProductDAO productDAO;
    private NameValidator nameValidator;

    @Autowired
    public void setProductDao(ProductDAO productDao){
        this.productDAO = productDao;
    }

    @Autowired
    public void setNameValidator(NameValidator nameValidator){this.nameValidator = nameValidator;}

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @GetMapping
    public String getAllProducts(Model model){
        List<Product> products = productDAO.selectAllProduct();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model){
        model.addAttribute("product", new Product(1,"",0,""));
        return "add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "add";
        }
        productDAO.insertProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productDAO.selectProduct(id));
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute @Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "edit";
        }

        productDAO.updateProduct(id, product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productDAO.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
