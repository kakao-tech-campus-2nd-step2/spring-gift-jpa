package gift.controller.product;

import gift.controller.auth.AuthController;
import gift.controller.auth.LoginResponse;
import gift.login.LoginMember;
import gift.service.ProductService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.find(productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@LoginMember LoginResponse loginMember,
        @RequestBody ProductRequest product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@LoginMember LoginResponse loginMember,
        @PathVariable UUID productId, @RequestBody ProductRequest product) {
        AuthController.validateAdmin(loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(productId, product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@LoginMember LoginResponse loginMember,
        @PathVariable UUID productId) {
        AuthController.validateAdmin(loginMember);
        productService.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
