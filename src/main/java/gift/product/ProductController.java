package gift.product;

import gift.exception.InvalidProduct;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public List<Product> getAllProducts() {
        return productService.getAllPrdouct();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product == null) {
            throw new InvalidProduct("유효하지 않은 상품입니다");
        }

        return new ProductResponseDto(
            product.get().getId(),
            product.get().getName(),
            product.get().getPrice(),
            product.get().getImageUrl()
        );
    }

    @PostMapping
    public Product addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto.name(), productRequestDto.price(), productRequestDto.url());
        return productService.postProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productService.putProduct(id, productRequestDto);
        return productService.postProduct(product);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}