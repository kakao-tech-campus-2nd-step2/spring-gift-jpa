package gift.product.controller;

import gift.product.model.ProductRepository;
import gift.product.model.dto.CreateProductRequest;
import gift.product.model.dto.ProductResponse;
import gift.product.model.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import java.util.List;
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
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id) {
        final ProductResponse response = productRepository.findProduct(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProduct() {
        final List<ProductResponse> response = productRepository.findAllProduct();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        if (productRepository.addProduct(createProductRequest) > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
        }
        throw new IllegalArgumentException("상품 추가 실패");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        if (productRepository.updateProduct(updateProductRequest) > 0) {
            return ResponseEntity.ok().body("ok");
        }
        throw new IllegalArgumentException("상품 수정 실패");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        if (productRepository.deleteProduct(id) > 0) {
            return ResponseEntity.ok().body("ok");
        }
        throw new IllegalArgumentException("상품 삭제 실패");
    }
}
