package gift.product;

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

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productDao.findAllProduct();

        return products.stream()
            .map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getUrl()
            )).toList();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        Optional<Product> product = productDao.findProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("유효하지 않은 상품입니다");
        }
        return new ProductResponseDto(
            product.get().getId(),
            product.get().getName(),
            product.get().getPrice(),
            product.get().getUrl()
        );
    }

    @PostMapping
    public ProductResponseDto addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = new Product(
            productRequestDto.name(),
            productRequestDto.price(),
            productRequestDto.url()
        );

        productDao.addProduct(product);

        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getUrl()
        );
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        Optional<Product> product = productDao.findProductById(id);
        if (product.isPresent()) {
            productDao.updateProductById(id, productRequestDto);
        } else {
            throw new NoSuchElementException("유효하지 않은 상품입니다");
        }

        return new ProductResponseDto(
            product.get().getId(),
            product.get().getName(),
            product.get().getPrice(),
            product.get().getUrl()
        );
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deleteProduct(@PathVariable Long id) {
        if (productDao.findProductById(id).isEmpty()) {
            throw new NoSuchElementException("유효하지 않은 상품입니다.");
        }
        else {
            productDao.deleteProductById(id);
        }
        return ResponseEntity.ok("성공적으로 삭제되었습니다");
    }
}
