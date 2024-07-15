package gift.doamin.product.controller;

import gift.doamin.product.dto.ProductForm;
import gift.doamin.product.dto.ProductParam;
import gift.doamin.product.service.ProductService;
import gift.doamin.user.entity.UserRole;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductParam addNewProduct(@Valid @RequestBody ProductForm productForm,
        Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        productForm.setUserId(userId);
        return productService.create(productForm);
    }

    @GetMapping
    public Page<ProductParam> getProducts(
        @RequestParam(required = false, defaultValue = "0", name = "page") int pageNum) {
        return productService.getPage(pageNum);
    }

    @GetMapping("/{id}")
    public ProductParam getOneProduct(@PathVariable Long id) {
        return productService.readOne(id);
    }

    @PutMapping("/{id}")
    public ProductParam updateProduct(@PathVariable Long id, @RequestBody ProductForm productForm,
        Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        boolean isSeller = authentication.getAuthorities()
            .contains(new SimpleGrantedAuthority(UserRole.SELLER.getValue()));
        productForm.setUserId(userId);

        return productService.update(id, productForm, isSeller);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        boolean isSeller = authentication.getAuthorities()
            .contains(new SimpleGrantedAuthority(UserRole.SELLER.getValue()));

        productService.delete(userId, id, isSeller);
    }
}
