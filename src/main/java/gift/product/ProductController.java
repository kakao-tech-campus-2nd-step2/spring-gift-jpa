package gift.product;

import gift.exception.InvalidProduct;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
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

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return productService.getProductById(id)
            .orElseThrow(() -> new InvalidProduct("유효하지 않은 상품입니다"));
    }

    @PostMapping
    public ProductResponseDto addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return productService.postProduct(productRequestDto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        return productService.putProduct(id, productRequestDto);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}
