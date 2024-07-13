package gift.domain.product.controller;

import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.service.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> readAll(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return new ResponseEntity<>(productService.getAllProducts(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> read(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> create(
        @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable("id") Long id,
        @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(id, productRequest);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
