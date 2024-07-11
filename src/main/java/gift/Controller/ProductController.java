package gift.Controller;

import gift.Model.DTO.ProductDTO;
import gift.Model.Entity.ProductEntity;
import gift.Service.ProductService;
import gift.Valid.NameValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    private NameValidator nameValidator;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @PostMapping("/products")
    @ResponseBody
    public void addProduct(@RequestHeader("Bearer") String token, @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            productService.add(token, productDTO);

    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public void deleteProduct(@RequestHeader("Bearer") String token, @PathVariable Long id){
        productService.delete(token, id);
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public void updateProduct(@RequestHeader("Bearer") String token, @PathVariable Long id, @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            productService.edit(token, id, productDTO);
    }

    @GetMapping("/products")
    public List<ProductDTO> viewAllProducts(@RequestHeader("Bearer") String token){
        return productService.getAll(token);
    }

    @GetMapping("/products/{id}")
    public ProductDTO viewProduct(@RequestHeader("Bearer") String token, @PathVariable Long id){
        return productService.getById(token, id);
    }
}
