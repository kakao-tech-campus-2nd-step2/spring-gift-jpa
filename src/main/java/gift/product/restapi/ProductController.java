package gift.product.restapi;

import gift.core.PagedDto;
import gift.core.domain.product.Product;
import gift.core.domain.product.ProductService;
import gift.core.domain.product.exception.ProductNotFoundException;
import gift.product.restapi.dto.request.ProductCreateRequest;
import gift.product.restapi.dto.request.ProductUpdateRequest;
import gift.product.restapi.dto.response.PagedProductResponse;
import gift.product.restapi.dto.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public PagedProductResponse getAllProducts(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PagedDto<Product> pagedProducts = productService.findAll(pageable);
        return PagedProductResponse.from(pagedProducts);
    }

    @GetMapping("/api/products/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return ProductResponse.from(productService.get(id));
    }

    @PostMapping("/api/products")
    public void addProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        Product product = productOf(request);
        productService.createProduct(product);
    }

    @PutMapping("/api/products/{id}")
    public void updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        Product originalProduct = productService.get(id);
        if (originalProduct == null) {
            throw new ProductNotFoundException();
        }
        Product updatedProduct = originalProduct.applyUpdate(request.name(), request.price(), request.imageUrl());
        productService.updateProduct(updatedProduct);
    }

    @DeleteMapping("/api/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.remove(id);
    }

    private Product productOf(ProductCreateRequest request) {
        return new Product(
            0L,
                request.name(),
                request.price(),
                request.imageUrl()
        );
    }
}
