package gift.product.controller;

import gift.product.model.dto.CreateProductAdminRequest;
import gift.product.model.dto.CreateProductRequest;
import gift.product.model.dto.ProductResponse;
import gift.product.model.dto.UpdateProductRequest;
import gift.product.service.ProductService;
import gift.user.model.dto.AppUser;
import gift.user.resolver.LoginUser;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id) {
        final ProductResponse response = productService.findProductWithWishCount(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ProductResponse>> findAllProductPage(
            @PageableDefault(size = 10, sort = "wishCount", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> response = productService.findAllProductWithWishCountPageable(pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProduct() {
        final List<ProductResponse> response = productService.findAllProductWithWishCount();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@LoginUser AppUser loginAppUser,
                                             @Valid @RequestBody CreateProductRequest createProductRequest) {
        productService.addProduct(loginAppUser, createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PostMapping("/admin")
    public ResponseEntity<String> addProductForAdmin(@LoginUser AppUser loginAppUser,
                                                     @Valid @RequestBody CreateProductAdminRequest createProductRequest) {
        CreateProductRequest req = new CreateProductRequest(createProductRequest.name(), createProductRequest.price(),
                createProductRequest.imageUrl());
        productService.addProduct(loginAppUser, req);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@LoginUser AppUser loginAppUser, @PathVariable Long id,
                                                @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(loginAppUser, id, updateProductRequest);
        return ResponseEntity.ok().body("ok");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@LoginUser AppUser loginAppUser, @PathVariable Long id) {
        productService.deleteProduct(loginAppUser, id);
        return ResponseEntity.ok().body("ok");
    }
}
