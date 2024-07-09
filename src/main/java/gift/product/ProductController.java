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
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productService.getAllPrdouct();

        return products.stream()
            .map(product -> new ProductResponseDto(
                (product.getId()-1),
                product.getName(),
                product.getPrice(),
                product.getUrl()
            )).toList();
    }

    @GetMapping("/{id}")
    public Optional<ProductResponseDto> getProduct(@PathVariable Long id) {
        Optional<ProductResponseDto> product = productService.getProductById(id);
        if (product == null) {
            throw new InvalidProduct("유효하지 않은 상품입니다");
        }
        return product;
//        return new ProductResponseDto(
//            product.get().getId(),
//            product.get().getName(),
//            product.get().getPrice(),
//            product.get().getUrl()
//        );

    }

    @PostMapping
    public ProductResponseDto addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = new Product(
            productRequestDto.name(),
            productRequestDto.price(),
            productRequestDto.url()
        );

        productService.postProduct(product);

        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getUrl()
        );
    }

    @PutMapping("/{id}")
    public Optional<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        Optional<ProductResponseDto> product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.putProduct(id, productRequestDto);
        } else {
            throw new InvalidProduct("유효하지 않은 상품입니다");
        }
        return product;

//        return new ProductResponseDto(
//            product.get().getId(),
//            product.get().getName(),
//            product.get().getPrice(),
//            product.get().getUrl()
//        );
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deleteProduct(@PathVariable Long id) {
        if (productService.getProductById(id).isEmpty()) {
            throw new InvalidProduct("유효하지 않은 상품입니다.");
        }
        else {
            productService.deleteProductById(id);
        }
        return ResponseEntity.ok("성공적으로 삭제되었습니다");
    }
}
