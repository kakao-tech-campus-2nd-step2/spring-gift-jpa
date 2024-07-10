package gift.aspect;

import gift.exception.ProductNotFoundException;
import gift.model.product.ProductRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 상품 관련 Aspect 클래스
 */
@Component
@Aspect
public class ProductAspect {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 상품이 존재하는지 확인하는 어노테이션을 처리하는 메소드
     *
     * @param id 상품 ID
     */
    @Before("@annotation(CheckProductExists) && args(id,..)")
    public void checkProductExists(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("상품이 존재하지 않습니다.");
        }
    }
}
