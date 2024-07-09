package gift.product;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String getAllProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/add")
    public String showPostProduct(Model model){
        model.addAttribute("productDTO", new ProductDTO(1L,"1",1,"1"));
        return "add";
    }

    @GetMapping("/update/{id}")
    public String showPutProduct(@PathVariable("id") long id, Model model)
        throws NotFoundException {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("productDTO", product);
        return "update";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) throws NotFoundException {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }

    @PostMapping("/add")
    public String postProduct(@ModelAttribute @Valid ProductDTO product, BindingResult result, Model model){
        if(productService.existsByName(product.getName())){
            result.addError(new FieldError("productDTO", "name", "존재하는 이름입니다."));
        }
        if(result.hasErrors()){
            return "add";
        }
        productService.addProduct(product);
        model.addAttribute("productDTO",product);
        return "redirect:/api/products";
    }

    @PostMapping("/update/{id}")
    public String putProduct(@PathVariable("id") Long id,  @ModelAttribute @Valid ProductDTO product, BindingResult result)
        throws NotFoundException {
        if(productService.existsByName(product.getName())){
            result.addError(new FieldError("productDTO", "name", "존재하는 이름입니다."));
        }
        if(result.hasErrors()){
            return "update";
        }
        product = new ProductDTO(id,product.getName(),product.getPrice(),product.getImageUrl());
        productService.updateProduct(product);
        return "redirect:/api/products";
    }
}
