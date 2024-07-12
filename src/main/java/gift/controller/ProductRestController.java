package gift.controller;

import gift.dto.*;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody ProductRequestDto request) {
        productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ProductResponseDto product = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getPagedProducts(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getPagedProducts(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDto request) {
        productService.updateById(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}