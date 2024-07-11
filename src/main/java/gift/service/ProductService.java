package gift.service;

import gift.exception.ProductException;
import gift.model.Product;
import gift.model.dto.ProductRequestDto;
import gift.model.dto.ProductResponseDto;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(ProductResponseDto::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        return ProductResponseDto.from(productRepository.findById(id).get());
    }

    @Transactional
    public void insertProduct(ProductRequestDto productRequestDto) throws ProductException {
        productRepository.save(productRequestDto.toEntity());
    }

    @Transactional
    public void updateProductById(Long id, ProductRequestDto productRequestDto)
        throws ProductException {
        Product product = productRepository.findById(id).get();
        product.updateInfo(productRequestDto.toEntity());
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
