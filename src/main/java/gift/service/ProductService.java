package gift.service;

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

    public ProductResponseDto getProduct(Long id) {
        return ProductResponseDto.from(productDao.selectProductById(id));
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        productDao.insertProduct(productRequestDto.toEntity());
    }

    public void updateProductById(Long id, ProductRequestDto productRequestDto) {
        productDao.updateProductById(id, productRequestDto.toEntity());
    }

    public void deleteProductById(Long id) {
        productDao.deleteProductById(id);
    }
}
