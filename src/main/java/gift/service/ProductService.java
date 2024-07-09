package gift.service;

import gift.exception.ProductErrorCode;
import gift.exception.ProductException;
import gift.model.Product;
import gift.model.dto.ProductRequestDto;
import gift.model.dto.ProductResponseDto;
import gift.repository.ProductDao;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(ProductResponseDto::from)
            .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        return ProductResponseDto.from(productRepository.findById(id).get());
    }

    public void insertProduct(ProductRequestDto productRequestDto) throws ProductException {
        productRepository.save(productRequestDto.toEntity(null));
    }

    public void updateProductById(Long id, ProductRequestDto productRequestDto)
        throws ProductException {
        productRepository.save(productRequestDto.toEntity(id));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
