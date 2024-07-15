package gift.domain.product.controller;

import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import gift.domain.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid ProductDto productDto) {
        Product savedProduct = productService.create(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> readAll(Pageable pageable) {
        Page<Product> productList = productService.readAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> readById(@PathVariable("productId") long productId) {
        Product product = productService.readById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@PathVariable("productId") long productId, @RequestBody @Valid ProductDto productDto) {
        Product updatedProduct = productService.update(productId, productDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") long productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
