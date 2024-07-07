package gift;

import gift.product.exception.ProductAlreadyExistsException;
import gift.product.model.Product;
import gift.product.repository.ProductRepositoryImpl;
import gift.product.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ProductExceptionTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepositoryImpl productRepository;

    @BeforeEach
    public void setup() {
        // Set up in-memory H2 database and JdbcTemplate
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Initialize schema
        jdbcTemplate.execute("DROP TABLE IF EXISTS Product");
        jdbcTemplate.execute("CREATE TABLE Product (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "price BIGINT, " +
                "temperatureOption VARCHAR(255), " +
                "cupOption VARCHAR(255), " +
                "sizeOption VARCHAR(255), " +
                "imageurl VARCHAR(255))");

        productRepository = new ProductRepositoryImpl(jdbcTemplate);
        productService = new ProductService(productRepository);
    }

    @Test
    @DisplayName("중복된 데이터로 상품 추가 시 예외 발생 테스트")
    public void createProductWithDuplicateDataTest() {
        // given
        Product product = new Product();
        product.setName("아이스 아메리카노");
        product.setPrice(2500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        productService.createProduct(product);

        // when, then
        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(ProductAlreadyExistsException.class);
    }

    @Test
    @DisplayName("유효성 검사 실패 테스트 - 필수 입력 값 누락")
    public void validationFailureTestMissingRequiredFields() {
        // given
        Product product = new Product();
        product.setName(null);
        product.setPrice(2500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        // when, then
        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("유효성 검사 실패 테스트 - 가격이 0 미만")
    public void validationFailureTestNegativePrice() {
        // given
        Product product = new Product();
        product.setName("아이스 아메리카노");
        product.setPrice(-1L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        // when, then
        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("유효성 검사 실패 테스트 - 이름이 15자를 초과")
    public void validationFailureTestNameTooLong() {
        // given
        Product product = new Product();
        product.setName("아이스 아메리카노 스페셜 에디션");
        product.setPrice(2500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        // when, then
        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("유효성 검사 실패 테스트 - 허용되지 않은 특수 문자 포함")
    public void validationFailureTestInvalidSpecialCharacter() {
        // given
        Product product = new Product();
        product.setName("아이스@아메리카노");
        product.setPrice(2500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        // when, then
        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("유효성 검사 실패 테스트 - '카카오' 포함")
    public void validationFailureTestContainsKakao() {
        // given
        Product product = new Product();
        product.setName("카카오 아메리카노");
        product.setPrice(2500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        // when, then
        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
