package gift.product.controller;

import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.model.Product;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
            .map(this::convertToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()) {
            ProductResponse productResponse = convertToResponse(product.get());
            return ResponseEntity.ok(productResponse);
        }
        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = convertToEntity(productRequest);
        Product savedProduct = productService.save(product);
        ProductResponse productResponse = convertToResponse(savedProduct);
        return ResponseEntity.status(201).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id, @Valid @RequestBody ProductRequest updatedProductRequest) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.status(204).build();
        }
        Product updatedProduct = convertToEntity(updatedProductRequest);
        updatedProduct.setId(id);
        Product savedProduct = productService.save(updatedProduct);
        ProductResponse productResponse = convertToResponse(savedProduct);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.status(204).build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setImgUrl(product.getImgUrl());
        return response;
    }

    private Product convertToEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setImgUrl(request.getImgUrl());
        return product;
    }



}
