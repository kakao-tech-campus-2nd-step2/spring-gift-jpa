package gift.product.service;

import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 아직은 로직이 단순해서 service가 필요 없는 규모라고 생각하지만, 확장을 위해 service로 분리
@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    // dao를 호출해서 productDTO를 DB에 넣는 함수
    public void insertProduct(ProductRequestDto productRequestDto) {
    }

    // dao를 호출해서 DB에 담긴 로우를 반환하는 함수
    public ProductResponseDto selectProduct(long productId) {
        return new ProductResponseDto(0,null,0,null);
    }

    // 전체 제품 정보를 반환하는 함수
    public List<ProductResponseDto> selectProducts() {
        return new ArrayList<>();
    }

    // dao를 호출해서 특정 로우를 파라메터로 전달된 DTO로 교체하는 함수
    public void updateProduct(long productId, ProductRequestDto productRequestDto) {
//        productRepository.updateProduct(
//            new Product(productId, productRequestDto.name(), productRequestDto.price(),
//                productRequestDto.imageUrl()));
    }

    // dao를 호출해서 특정 로우를 제거하는 함수
    public void deleteProduct(long id) {

    }
}
