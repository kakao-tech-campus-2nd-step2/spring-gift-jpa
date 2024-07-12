package gift.domain.service;

import gift.domain.dto.request.ProductRequest;
import gift.domain.dto.response.ProductResponse;
import gift.domain.entity.Product;
import gift.domain.exception.ProductAlreadyExistsException;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse getProductById(Long id) {
        //존재하지 않는 상품 참조 시도시 예외 발생
        Optional<Product> product = productRepository.findById(id);
        product.orElseThrow(ProductNotFoundException::new);

        return ProductResponse.of(product.get());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductResponse::of)
            .toList();
    }

    public ProductResponse addProduct(ProductRequest requestDto) {
        //이미 존재하는 상품 등록 시도시 예외 발생
        productRepository.findByContents(requestDto).ifPresent((p) -> {
            throw new ProductAlreadyExistsException();
        });


        //상품 등록
        return ProductResponse.of(productRepository.save(requestDto));
    }

    public ProductResponse updateProductById(Long id, ProductRequest requestDto) {
        //존재하지 않는 상품 업데이트 시도시 예외 발생
        productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        //상품 업데이트
        return ProductResponse.of(productRepository.update(id, requestDto));
    }

    public void deleteProduct(Long id) {
        //존재하지 않는 상품 삭제 시도시 예외 발생
        productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        //상품 삭제
        productRepository.deleteById(id);
    }
}
