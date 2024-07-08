package gift.controller.product;

import gift.dto.Product;
import gift.dto.response.ProductIdResponse;
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
    public ResponseEntity<ProductIdResponse> addProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(new ProductIdResponse(productService.addProduct(product)), HttpStatus.CREATED);
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
