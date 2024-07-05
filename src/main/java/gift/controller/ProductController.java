package gift.controller;

import gift.model.ProductRecord;
import gift.repository.ProductDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ProductController {
    private final ProductDAO productDAO;

    ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductRecord>> getAllProducts() {
        return ResponseEntity.ok(productDAO.getAllRecords());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductRecord> addProduct(@Valid @RequestBody ProductRecord product) {
        ProductRecord result = productDAO.addNewRecord(product);

        return makeCreatedResponse(result);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productDAO.deleteRecord(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductRecord> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRecord product) {
        ProductRecord result;
        try {
            result = productDAO.replaceRecord(id, product);

            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            result =  productDAO.addNewRecord(product, id);

            return makeCreatedResponse(result);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductRecord> updateProductPartially(@PathVariable int id, @Valid @RequestBody ProductRecord patch) {
        return ResponseEntity.ok(productDAO.updateRecord(id, patch));
    }

    private ResponseEntity<ProductRecord> makeCreatedResponse(ProductRecord product) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/products/"+ product.id())
                .build()
                .toUri();

        return ResponseEntity.created(location).body(product);
    }
}
