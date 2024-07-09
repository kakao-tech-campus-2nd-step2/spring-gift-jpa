package gift.Controller;

import gift.Model.Product;
import gift.Service.ProductService;
import gift.Valid.NameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    private NameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @PostMapping("/products")
    @ResponseBody
    public void addProduct(@RequestHeader("Bearer") String token, @RequestBody Product product, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            productService.add(token, product);

    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public void deleteProduct(@RequestHeader("Bearer") String token, @PathVariable Long id){
        productService.delete(token, id);
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public void updateProduct(@RequestHeader("Bearer") String token, @PathVariable Long id, @RequestBody Product product, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            productService.edit(token, id, product);
    }

    @GetMapping("/products")
    public List<Product> viewAllProducts(@RequestHeader("Bearer") String token){
        return productService.getAll(token);
    }

    @GetMapping("/products/{id}")
    public Product viewProduct(@RequestHeader("Bearer") String token, @PathVariable Long id){
        Optional<Product> product = productService.getById(token, id);
        return product.orElse(null);
    }
}
