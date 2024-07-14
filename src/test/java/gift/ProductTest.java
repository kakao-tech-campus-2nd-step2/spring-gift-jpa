package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.product.Product;
import gift.product.ProductService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {
    @Autowired
    private ProductService productService;

    @DisplayName("비즈니스 로직 테스트")
    @Test
    void domain(){
        //given
        Product product = new Product(null, "카카오", 1234, null);

        //when
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, ()-> productService.createProduct(product));

        //then
        assertThat(exception.getMessage()).isEqualTo("createProduct.newProduct.name: \"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");

    }

}
