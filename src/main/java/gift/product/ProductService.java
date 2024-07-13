package gift.product;

import gift.exception.InvalidProduct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getAllPrdouct() {
        return productRepository.findAll().stream()
            .map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()))
            .collect(Collectors.toList());
    }

    public Optional<ProductResponseDto> getProductById(Long id) {
        return productRepository.findById(id)
            .map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()));
    }

    public ProductResponseDto postProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.saveAndFlush(new Product(
            productRequestDto.name(),
            productRequestDto.price(),
            productRequestDto.url()
        ));
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    public ProductResponseDto putProduct(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new InvalidProduct("유효하지 않은 상품입니다"));

        product.update(productRequestDto.name(), productRequestDto.price(), productRequestDto.url());
        productRepository.saveAndFlush(product);

        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    public HttpEntity<String> deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new InvalidProduct("유효하지 않은 상품입니다");
        } else {
            productRepository.deleteById(id);
        }
        return ResponseEntity.ok("성공적으로 삭제되었습니다");
    }

}
