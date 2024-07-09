package gift.doamin.product.controller;

import gift.doamin.product.entity.Product;
import gift.doamin.product.repository.ProductRepository;
import gift.doamin.product.service.ProductService;
import gift.doamin.user.entity.UserRole;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpRequest;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    public ProductsController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addNewProduct(@Valid @RequestBody Product product, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        product.setUserId(userId);
        return productService.create(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.readAll();
    }

    @GetMapping("/{id}")
    public Product getOneProduct(@PathVariable Long id) {
        return productService.readOne(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product,
        Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        boolean isSeller = authentication.getAuthorities()
            .contains(new SimpleGrantedAuthority(UserRole.SELLER.getValue()));
        product.setId(id);

        return productService.update(userId, product, isSeller);
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
