package gift.domain.product.controller;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import gift.exception.InvalidProductInfoException;
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
public class ProductRestController {

    private final ProductJpaRepository productJpaRepository;

    public ProductRestController(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid ProductDto productDto) {
        Product product = productDto.toProduct();
        Product savedProduct = productJpaRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> readAll() {
        List<Product> productList = productJpaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> readById(@PathVariable("productId") long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@PathVariable("productId") long productId, @RequestBody @Valid ProductDto productDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setImageUrl(productDto.imageUrl());

        Product updatedProduct = productJpaRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        productJpaRepository.delete(product);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
