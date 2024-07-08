package gift.Controller;

import gift.Exception.AuthorizedException;
import gift.Exception.LoginException;
import gift.Model.Product;
import gift.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/products")
    @ResponseBody
    public void addProduct(@RequestHeader("bearer") String token, @RequestBody Product product){
        productService.add(token, product);
    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public void deleteProduct(@RequestHeader("bearer") String token, @PathVariable Long id){
        productService.delete(token, id);
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public void updateProduct(@RequestHeader("bearer") String token, @PathVariable Long id, @RequestBody Product product){
        productService.edit(token, id, product);
    }

    @GetMapping("/products")
    public List<Product> viewAllProducts(@RequestHeader("bearer") String token){
        return productService.getAll(token);
    }

    @GetMapping("/products/{id}")
    public Product viewProduct(@RequestHeader("bearer") String token, @PathVariable Long id){
        return productService.getById(token, id);
    }
}
