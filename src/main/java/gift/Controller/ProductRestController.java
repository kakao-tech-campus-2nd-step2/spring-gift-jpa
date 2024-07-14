package gift.Controller;

import gift.Model.DTO.ProductDTO;
import gift.Service.ProductService;
import gift.Valid.NameValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private final ProductService productService;
    private NameValidator nameValidator;

    public ProductRestController(ProductService productService){
        this.productService = productService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @PostMapping("/products")
    @ResponseBody
    public void addProduct(@RequestAttribute("Email") String email, @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            productService.add(email, productDTO);

    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public void deleteProduct(@RequestAttribute("Email") String email, @PathVariable Long id){
        productService.delete(email, id);
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public void updateProduct(@RequestAttribute("Email") String email, @PathVariable Long id, @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            productService.edit(email, id, productDTO);
    }

    @GetMapping("/products")
    public List<ProductDTO> viewAllProducts(@RequestAttribute("Email") String email){
        return productService.getAll(email);
    }

    @GetMapping("/products/{id}")
    public ProductDTO viewProduct(@RequestAttribute("Email") String email, @PathVariable Long id){
        return productService.getById(email, id);
    }
}
