package gift.product;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 모든 상품 조회
    public List<Product> getAllPrdouct() {
        return productRepository.findAll();
    }

    // 아이디로 상품이 존재하는지 확인이 아니고 상품 조회
    public ProductResponseDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        Product pd = product.get();
        return new ProductResponseDto(pd.getId(), pd.getName(), pd.getPrice(), pd.getImageUrl());
    }

    // 상품 인서트
    public Product postProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    // 상품 정보 수정
    public Product putProduct(Long id, ProductRequestDto productRequestDto) {
        Optional<Product> product = productRepository.findById(id);

        Product pd = product.get();
        pd.update(productRequestDto.name(), productRequestDto.price(), productRequestDto.url());

        return pd;
    }

    // 상품 삭제
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
