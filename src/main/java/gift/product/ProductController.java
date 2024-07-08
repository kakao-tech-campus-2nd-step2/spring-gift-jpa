package gift.product;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/add")
    public String showPostProduct(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        return "add";
    }

    @GetMapping("/update/{id}")
    public String showPutProduct(@PathVariable("id") Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("productDTO", product);
        return "update";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }

    @PostMapping("/add")
    public String postProduct(@ModelAttribute @Valid ProductDTO product, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add";
        }
        productService.addProduct(product);
        model.addAttribute("productDTO",product);
        return "redirect:/api/products";
    }

    @PostMapping("/update/{id}")
    public String putProduct(@PathVariable("id") Long id,  @ModelAttribute @Valid ProductDTO product, BindingResult result){
        if(result.hasErrors()){
            return "update";
        }
        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/api/products";
    }
}
