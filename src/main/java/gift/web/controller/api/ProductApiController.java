package gift.web.controller.api;

import gift.authentication.annotation.LoginMember;
import gift.service.ProductService;
import gift.service.WishProductService;
import gift.web.dto.MemberDetails;
import gift.web.dto.request.product.CreateProductRequest;
import gift.web.dto.request.product.UpdateProductRequest;
import gift.web.dto.request.wishproduct.CreateWishProductRequest;
import gift.web.dto.response.product.CreateProductResponse;
import gift.web.dto.response.product.ReadAllProductsResponse;
import gift.web.dto.response.product.UpdateProductResponse;
import gift.web.dto.response.wishproduct.CreateWishProductResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
public class ProductApiController {

    private final ProductService productService;
    private final WishProductService wishProductService;

    public ProductApiController(ProductService productService, WishProductService wishProductService) {
        this.productService = productService;
        this.wishProductService = wishProductService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(
        @Validated @RequestBody CreateProductRequest request) throws URISyntaxException {
        CreateProductResponse response = productService.createProduct(request);

        URI location = new URI("http://localhost:8080/api/products/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/wish")
    public ResponseEntity<CreateWishProductResponse> createWishProduct(@Validated @RequestBody CreateWishProductRequest request, @LoginMember MemberDetails memberDetails) {
        CreateWishProductResponse response = wishProductService.createWishProduct(memberDetails.getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ReadAllProductsResponse> readAllProducts(@PageableDefault Pageable pageable) {
        ReadAllProductsResponse response = productService.readAllProducts(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateProductResponse> updateProduct(@PathVariable Long id, @Validated @RequestBody UpdateProductRequest request) {
        UpdateProductResponse response;
        try {
            response = productService.updateProduct(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
