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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this. productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts(@RequestParam int page, @RequestParam int size,
        @RequestParam String criterion, @RequestParam String direction) {

        return ResponseEntity.ok()
            .body(productService.getProducts(page, size, criterion, direction));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.created(
            URI.create("/api/products/" + productService.add(productRequest))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @Valid @RequestBody ProductRequest productRequest) {
        productService.update(id, productRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
