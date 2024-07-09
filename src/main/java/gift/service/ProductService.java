package gift.service;

import gift.exception.ProductErrorCode;
import gift.exception.ProductException;
import gift.model.dto.ProductRequestDto;
import gift.model.dto.ProductResponseDto;
import gift.repository.ProductDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productDao.selectAllProduct()
            .stream()
            .map(ProductResponseDto::from)
            .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        return ProductResponseDto.from(productDao.selectProductById(id));
    }

    public void insertProduct(ProductRequestDto productRequestDto) throws ProductException {
        validateKakaoWord(productRequestDto.getName());
        productDao.insertProduct(productRequestDto.toEntity());
    }

    public void updateProductById(Long id, ProductRequestDto productRequestDto) throws ProductException {
        validateKakaoWord(productRequestDto.getName());
        productDao.updateProductById(id, productRequestDto.toEntity());
    }

    public void deleteProductById(Long id) {
        productDao.deleteProductById(id);
    }

    private void validateKakaoWord(String name) throws ProductException {
        if (name.contains("카카오")) {
            throw new ProductException(ProductErrorCode.HAS_KAKAO_WORD);
        }
    }
}
