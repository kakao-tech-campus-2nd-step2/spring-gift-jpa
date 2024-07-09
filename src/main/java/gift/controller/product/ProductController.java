package gift.controller.product;

import gift.controller.product.dto.ProductResponse.ProductInfoResponse;
import gift.global.auth.Authorization;
import gift.controller.product.dto.ProductRequest.ProductRegisterRequest;
import gift.controller.product.dto.ProductRequest.ProductUpdateRequest;

import gift.global.dto.PageResponse;
import gift.model.member.Role;
import gift.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductInfoResponse> getProduct(
        @PathVariable("id") Long id
    ) {
        var response = productService.getProduct(id);
        return ResponseEntity.ok().body(response);
    }

    @Authorization(role = Role.ADMIN)
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(
        @RequestBody @Valid ProductRegisterRequest request
    ) {
        productService.createProduct(request);
        return ResponseEntity.ok().body("Product created successfully.");
    }


    @Authorization(role = Role.ADMIN)
    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(
        @PathVariable("id") Long id,
        @RequestBody @Valid ProductUpdateRequest request
    ) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok().body("Product updated successfully.");
    }

    @Authorization(role = Role.ADMIN)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable("id") Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<PageResponse<ProductInfoResponse>> getProductsPaging(
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var response = productService.getProductsPaging(page, size);
        return ResponseEntity.ok().body(response);
    }

}
