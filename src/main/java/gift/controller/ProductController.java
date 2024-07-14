package gift.controller;

import gift.config.PageConfig;
import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
        @PageableDefault(
            size = PageConfig.PAGE_PER_COUNT,
            sort = PageConfig.SORT_STANDARD,
            direction = Direction.DESC
        ) Pageable pageable) {
        try {
            Page<ProductResponse> products = productService.getAllProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody @Valid AddProductRequest request) {
        Long productId = productService.addProduct(request);
        return new ResponseEntity<>(productId, getProductLocationHeader(productId),
            HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody @Valid
    UpdateProductRequest request) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders getProductLocationHeader(Long productId) {
        HttpHeaders headers = new HttpHeaders();
        URI location = UriComponentsBuilder.newInstance()
            .path("api/products/{id}")
            .buildAndExpand(productId)
            .toUri();
        headers.setLocation(location);
        return headers;
    }
}
