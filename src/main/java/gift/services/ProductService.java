package gift.services;
import gift.Product;
import gift.ProductDto;
import gift.repositories.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private long currentId = 1;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 모든 제품 조회
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(product -> new ProductDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        )).toList();

        return productDtos;
    }

    // 특정 제품 조회
    public ProductDto getProductById(Long id) {
        Product product = productRepository.find(id);
        if (product == null) {
            throw new NoSuchElementException("Product not found with id " + id);
        }
        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
        return productDto;
    }

    // 제품 추가
    public ProductDto addProduct(@Valid ProductDto productDto) {
        Product product = new Product(
            null,
            productDto.getName(),
            productDto.getPrice(),
            productDto.getImageUrl()
        );
//        validationService.checkValid(product);

        if (product.getId() == null) {
            product.setId(currentId++);
        }
        productRepository.insert(product);

        ProductDto savedProductDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());

        return savedProductDto;
    }

    // 제품 수정
    public ProductDto updateProduct(@Valid ProductDto productDto) {
        Product product = new Product(
            productDto.getId(),
            productDto.getName(),
            productDto.getPrice(),
            productDto.getImageUrl()
        );

        productRepository.update(product);
        ProductDto updatedProductDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());

        return updatedProductDto;
    }

    // 제품 삭제
    public void deleteProduct(Long id) {
        productRepository.remove(id);
    }
}