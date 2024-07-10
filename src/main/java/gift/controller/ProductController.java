package gift.controller;

import gift.exception.ResourceNotFoundException;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product result = repository.findById(id);
        if (result == null) throw new ResourceNotFoundException("Product not found with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Product> postProduct(@RequestBody @Valid ProductDTO form) {
        Product result = repository.save(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Product> putProduct(@RequestBody @Valid ProductDTO form,
                                              @PathVariable("id") Long id) {
        Product result = repository.edit(id, form);
        if (result == null) throw new ResourceNotFoundException("Unable to update product with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        boolean result = repository.delete(id);
        if (result == false) throw new ResourceNotFoundException("Unable to delete product with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }
}
