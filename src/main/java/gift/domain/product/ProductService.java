package gift.domain.product;

import gift.global.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final JdbcTemplate jdbcTemplate; // h2 DB 사용한 메모리 저장 방식
    private final JpaProductRepository productRepository;
    private final Validator validator;

    @Autowired
    public ProductService(JdbcTemplate jdbcTemplate, JpaProductRepository jpaProductRepository,
        Validator validator) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = jpaProductRepository;
        this.validator = validator;
    }

    /**
     * 상품 추가
     */
    public void createProduct(ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 이름의 상품이 이미 존재합니다.");
        }

        Product product = new Product(productDTO.getName(), productDTO.getPrice(),
            productDTO.getImageUrl());

        validateProduct(product);

        productRepository.save(product);
    }

    /**
     * 전체 싱픔 목록 조회 - 페이징(매개변수별)
     */
    public Page<Product> getProductsByPageAndSort(int page, int size, Sort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageRequest);

        return products;
    }

    /**
     * 상품 수정
     */
    public void updateProduct(Long id, ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 이름의 상품이 이미 존재합니다.");
        }

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "수정할 상품이 존재하지 않습니다."));

        product.update(productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl());

        validateProduct(product);

        productRepository.save(product);
    }

    /**
     * 상품 삭제
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * 해당 ID 리스트에 속한 상품들 삭제
     */
    public void deleteProductsByIds(List<Long> productIds) {
        if (productIds.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "삭제할 상품을 선택하세요.");
        }

        productRepository.deleteAllByIdIn(productIds);
    }

    /**
     * 비즈니스 제약 사항 검사
     */
    public void validateProduct(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new BusinessException(HttpStatus.BAD_REQUEST, message);
        }
    }

}


