package gift.product.service;

import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.entity.Product;
import gift.product.repository.ProductDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 아직은 로직이 단순해서 service가 필요 없는 규모라고 생각하지만, 확장을 위해 service로 분리
@Service
public class ProductService {

    private long productId = 4;
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    // dao를 호출해서 productDTO를 DB에 넣는 함수
    public void insertProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(getProductId(), productRequestDto.name(),
            productRequestDto.price(), productRequestDto.imageUrl());

        productDao.insertProduct(product);
    }

    // dao를 호출해서 DB에 담긴 로우를 반환하는 함수
    public ProductResponseDto selectProduct(long productId) {
        return productDao.selectProduct(productId);
    }

    // 전체 제품 정보를 반환하는 함수
    public List<ProductResponseDto> selectProducts() {
        return productDao.selectProducts();
    }

    // dao를 호출해서 특정 로우를 파라메터로 전달된 DTO로 교체하는 함수
    public void updateProduct(long productId, ProductRequestDto productRequestDto) {
        productDao.updateProduct(
            new Product(productId, productRequestDto.name(), productRequestDto.price(),
                productRequestDto.imageUrl()));
    }

    // dao를 호출해서 특정 로우를 제거하는 함수
    public void deleteProduct(long id) {
        productDao.deleteProduct(id);
    }

    private long getProductId() {
        return productId++;
    }
}
