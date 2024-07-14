package gift.controller.product;

import gift.controller.product.dto.ProductRequest;
import gift.controller.product.dto.ProductResponse;
import gift.global.auth.Authorization;
import gift.global.dto.PageResponse;
import gift.model.member.Role;
import gift.model.product.SearchType;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ProductResponse.Info> getProduct(
        @PathVariable("id") Long id
    ) {
        var response = productService.getProduct(id);
        return ResponseEntity.ok().body(response);
    }

    @Authorization(role = Role.ADMIN)
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(
        @RequestBody @Valid ProductRequest.Register request
    ) {
        productService.createProduct(request);
        return ResponseEntity.ok().body("Product created successfully.");
    }


    @Authorization(role = Role.ADMIN)
    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(
        @PathVariable("id") Long id,
        @RequestBody @Valid ProductRequest.Update request
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
    public ResponseEntity<PageResponse<ProductResponse.Info>> getProductsPaging(
        @RequestParam(name = "SearchType", required = false, defaultValue = "ALL") SearchType searchType,
        @RequestParam(name = "SearchValue", required = false, defaultValue = "") String searchValue,
        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var response = productService.getProductsPaging(searchType, searchValue, pageable);
        return ResponseEntity.ok().body(response);
    }

}
