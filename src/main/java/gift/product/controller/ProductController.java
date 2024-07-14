package gift.product.controller;

import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.model.Product;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductResponse> response = products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ProductResponse>> getPagedProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productService.findAll(pageable);
        Page<ProductResponse> responsePage = productsPage.map(ProductResponse::from);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ProductResponse productResponse = ProductResponse.from(product.get());
            return ResponseEntity.ok(productResponse);
        }
        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(
        @Valid @RequestBody ProductRequest productRequest) {
        Product product = ProductRequest.toEntity(productRequest);
        Product savedProduct = productService.save(product);
        ProductResponse productResponse = ProductResponse.from(savedProduct);
        return ResponseEntity.status(201).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductRequest updatedProductRequest) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.status(204).build();
        }
        Product updatedProduct = ProductRequest.toEntity(updatedProductRequest);
        updatedProduct.setId(id);
        Product savedProduct = productService.save(updatedProduct);
        ProductResponse productResponse = ProductResponse.from(savedProduct);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.status(204).build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
