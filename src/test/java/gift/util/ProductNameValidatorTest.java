package gift.util;

import gift.entity.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
@Transactional
public class ProductNameValidatorTest {

    @Autowired
    private Validator validator;

    @Autowired
    private ProductService productService;

    @Test
    public void save_nameSuccess() {
        //given
        ProductDTO product = new ProductDTO("test", 123, "www.test.com");

        //when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //then
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    public void save_nameLongerThan15Char_eng() {
        //given
        String wrongName = "a".repeat(16);
        ProductDTO product = new ProductDTO(wrongName, 123, "www.test.com");

        //when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    public void save_nameLongerThan15Char_kor() {
        //given
        String wrongName = "가나다라마바사아자차카타파하호우";
        ProductDTO product = new ProductDTO(wrongName, 123, "www.test.com");

        //when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    public void save_nameWithStrangeChar() {
        //given
        String wrongName = "W̡̧̤̜͉̝̼̌̎͛̎̽͒̆̀̌R̢̪̟̯̣̄͑̾͘͡O͇̭̬̘̦̦̭̮͌͐̍́̈́͢͝͝N̞͕̝͚̹̹̓̑͊̊̅͐̎̈́͘͜G̶̛̬͖̙̭̩̾͗͐̽͋̑͜͢_̴̢͖̫̙̗̺̘̫̱̐̓̓͑̾N̠͙̙͓̬̣̾͒̆̋̋͜͝Ȧ̜̙̩̦̘̯͕̰̜͛͆̎́̚͘͝M̸̡̛̲̻̙͔͎̳̄̈̈́͊̉̌̄̕͞ͅȆ̵̤̱̖̻̳̹̰͚̣̍̾͆̈̕̕͝͡͝";
        ProductDTO product = new ProductDTO(wrongName, 123, "www.test.com");

        //when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    public void save_nameWithKakao() {
        //given
        String wrongName = "아프리카 직수입 카카오";
        ProductDTO product = new ProductDTO(wrongName, 123, "www.test.com");

        //when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }
}
