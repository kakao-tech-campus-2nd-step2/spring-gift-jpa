package gift.service.product;

import gift.domain.product.Product;
import gift.domain.product.ProductReposiotory;
import gift.web.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Service;
// Service단에서는 DTO를 Entity로 변환해서 Repository로 넘겨주고, Entity를 DTO로 변환해서 Controller에서 넘겨주면 되나?
@Service
public class ProductService {
    private final ProductReposiotory productReposiotory;

    public ProductService(ProductReposiotory productReposiotory) {
        this.productReposiotory = productReposiotory;
    }

    public List<ProductDto> getProducts() {
        return List.copyOf(productReposiotory.selectAllProducts()
                            .stream()
                            .map(ProductDto::from)
                            .toList()
                            );
    }

    public ProductDto getProductById(Long id) {
        return ProductDto.from(productReposiotory.selectProductById(id));
    }

    public ProductDto createProduct(ProductDto productDto) {
        return ProductDto.from(productReposiotory.insertProduct(ProductDto.toEntity(productDto)));
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        productReposiotory.selectProductById(id);
        Product newProduct = new Product(id, productDto.name(), productDto.price(), productDto.imageUrl());
        productReposiotory.updateProduct(newProduct);
        return ProductDto.from(newProduct);
    }

    public void deleteProduct(Long id) {
        productReposiotory.selectProductById(id);
        productReposiotory.deleteProductById(id);
    }
}
