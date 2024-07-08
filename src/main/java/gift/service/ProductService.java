package gift.service;

import gift.domain.repository.ProductJdbcRepository;
import gift.domain.model.ProductDto;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJdbcRepository productJdbcRepository;

    public ProductService(ProductJdbcRepository productJdbcRepository) {
        this.productJdbcRepository = productJdbcRepository;
    }

    public ProductDto getProduct(Long id) {
        if (!productJdbcRepository.isExistProductId(id)) {
            throw new NoSuchElementException("Invalid Product ID");
        }
        return productJdbcRepository.getProductById(id);
    }

    public List<ProductDto> getAllProduct() {
        return productJdbcRepository.getAllProduct();
    }

    public ProductDto addProduct(ProductDto productDto) {
        validateProductName(productDto.getName());
        validateDuplicateProductId(productDto.getId());
        validateDuplicateProduct(productDto.getName());
        productJdbcRepository.addProduct(productDto);
        return productDto;
    }

    private void validateDuplicateProductId(Long id) {
        if (productJdbcRepository.isExistProductId(id)) {
            throw new NoSuchElementException("이미 존재하는 상품 ID입니다.");
        }
    }


    private void validateDuplicateProduct(String name) {
        if (productJdbcRepository.isexistProductName(name)) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }
    }

    private void validateProductName(String name) {
        if (name.contains("카카오")) {
            throw new IllegalArgumentException("'카카오'가 포함된 상품명은 담당 MD와 협의가 필요합니다.");
        }
    }

    public void updateProduct(ProductDto productDto) {
        validateProductName(productDto.getName());
        validateExistProductId(productDto.getId());
        validateDuplicateProduct(productDto.getName());
        productJdbcRepository.updateProduct(productDto);
    }

    protected void validateExistProductId(Long id) {
        if (!productJdbcRepository.isExistProductId(id)) {
            throw new NoSuchElementException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteProduct(Long id) {
        if (!productJdbcRepository.isExistProductId(id)) {
            throw new NoSuchElementException("Invalid Product ID");
        }
        productJdbcRepository.deleteProduct(id);
    }
}