package gift.controller.product;

import gift.dto.request.ProductRequest;
import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("api/products")
    public ResponseEntity<AddedProductIdResponse> addProduct(@Valid @RequestBody ProductRequest addRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProduct(addRequest));
    }

    @GetMapping("api/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("api/products")
    public ResponseEntity<ProductIdResponse> updateProduct(@Valid @RequestBody Product product) {
        boolean isUpdated = productService.updateProduct(product);
        if (isUpdated) {
            return new ResponseEntity<>(new ProductIdResponse(product.getId()), HttpStatus.OK);
        }
        Long createdProductId = productService.addProduct(product);
        if (createdProductId != -1L) {
            return new ResponseEntity<>(new ProductIdResponse(createdProductId), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("api/products/{id}")
    public ResponseEntity<ProductIdResponse> deleteProduct(@PathVariable("id") Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return new ResponseEntity<>(new ProductIdResponse(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
