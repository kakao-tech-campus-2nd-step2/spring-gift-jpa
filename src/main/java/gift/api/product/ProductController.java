package gift.api.product;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Void> add(@Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(
            productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return ResponseEntity.created(
            URI.create("/api/products/" + productRepository.save(product).getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @Valid @RequestBody ProductRequest productRequest) {
        productRepository.save(new Product(
            id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
