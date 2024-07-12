package gift.service.product;

import gift.domain.product.Product;
import gift.domain.product.ProductReposiotory;
import gift.web.dto.ProductDto;
import gift.web.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
// Service단에서는 DTO를 Entity로 변환해서 Repository로 넘겨주고, Entity를 DTO로 변환해서 Controller에서 넘겨주면 되나?
@Service
public class ProductService {
    private final ProductReposiotory productReposiotory;

    public ProductService(ProductReposiotory productReposiotory) {
        this.productReposiotory = productReposiotory;
    }

    public List<ProductDto> getProducts() {
        return productReposiotory.findAll()
            .stream()
            .map(ProductDto::from)
            .toList();
    }

    public ProductDto getProductById(Long id) {
        return productReposiotory.findById(id)
            .map(ProductDto::from)
            .orElseThrow(() -> new ProductNotFoundException("제품이 없슴다."));
    }

    public ProductDto createProduct(ProductDto productDto) {
        return ProductDto.from(productReposiotory.save(ProductDto.toEntity(productDto)));
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        productReposiotory.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("제품이 없슴다."));
        Product newProduct = ProductDto.toEntity(productDto);
        productReposiotory.save(newProduct);
        return ProductDto.from(newProduct);
    }

    public void deleteProduct(Long id) {
        productReposiotory.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("제품이 없슴다."));
        productReposiotory.deleteById(id);
    }
}
