package gift;

import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.repository.ProductRepositoryImpl;
import gift.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        // Set up in-memory H2 database and JdbcTemplate
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("password");

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
    @DisplayName("상품 추가 테스트")
    public void createProductTest() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setName("아이스 아메리카노");
        product.setPrice(2500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");

        // when
        Product createdProduct = productService.createProduct(product);

        // then
        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("아이스 아메리카노");
        assertThat(createdProduct.getPrice()).isEqualTo(2500L);
        assertThat(createdProduct.getTemperatureOption()).isEqualTo("Ice");
        assertThat(createdProduct.getCupOption()).isEqualTo("일회용컵");
        assertThat(createdProduct.getSizeOption()).isEqualTo("tall");
        assertThat(createdProduct.getImageurl()).isEqualTo("https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724");
    }

    @Test
    @DisplayName("모든 상품 조회 테스트")
    public void getAllProductsTest() {
        // given
        Product product1 = new Product();
        product1.setName("아이스 카페 아메리카노 T");
        product1.setPrice(4500L);
        product1.setTemperatureOption("Ice");
        product1.setCupOption("일회용컵");
        product1.setSizeOption("tall");
        product1.setImageurl("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        Product product2 = new Product();
        product2.setName("아이스 카페라떼 T");
        product2.setPrice(3500L);
        product2.setTemperatureOption("Ice");
        product2.setCupOption("일회용컵");
        product2.setSizeOption("tall");
        product2.setImageurl("https://market.shosyn.com/assets/data/product/_product_image_2368.jpeg?u=1568979271");

        productService.createProduct(product1);
        productService.createProduct(product2);

        // when
        List<Product> products = productService.getAllProducts();

        // then
        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("특정 id의 상품 조회 테스트")
    public void getProductByIdTest() {
        // given
        Product product1 = new Product();
        product1.setName("아이스 카페 아메리카노 T");
        product1.setPrice(4500L);
        product1.setTemperatureOption("Ice");
        product1.setCupOption("일회용컵");
        product1.setSizeOption("tall");
        product1.setImageurl("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        Product product2 = new Product();
        product2.setName("아이스 카페라떼 T");
        product2.setPrice(3500L);
        product2.setTemperatureOption("Ice");
        product2.setCupOption("일회용컵");
        product2.setSizeOption("tall");
        product2.setImageurl("https://market.shosyn.com/assets/data/product/_product_image_2368.jpeg?u=1568979271");

        productService.createProduct(product1);
        productService.createProduct(product2);

        // when
        Product foundProduct = productService.getProductById(1L);

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(1L);
        assertThat(foundProduct.getName()).isEqualTo("아이스 카페 아메리카노 T");
        assertThat(foundProduct.getPrice()).isEqualTo(4500L);
        assertThat(foundProduct.getImageurl()).isEqualTo("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    }

    @Test
    @DisplayName("상품 수정 테스트")
    public void updateProductTest() {
        // given
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("아이스 카페 아메리카노 T");
        product1.setPrice(4500L);
        product1.setTemperatureOption("Ice");
        product1.setCupOption("일회용컵");
        product1.setSizeOption("tall");
        product1.setImageurl("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        Product updatedProductData = new Product();
        updatedProductData.setName("아이스 카페라떼 T");
        updatedProductData.setPrice(3500L);
        updatedProductData.setTemperatureOption("Ice");
        updatedProductData.setCupOption("일회용컵");
        updatedProductData.setSizeOption("tall");
        updatedProductData.setImageurl("https://market.shosyn.com/assets/data/product/_product_image_2368.jpeg?u=1568979271");

        productService.createProduct(product1);

        // when
        Product updatedProduct = productService.updateProduct(updatedProductData);

        // then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("아이스 카페라떼 T");
        assertThat(updatedProduct.getPrice()).isEqualTo(3500L);
        assertThat(updatedProduct.getImageurl()).isEqualTo("https://market.shosyn.com/assets/data/product/_product_image_2368.jpeg?u=1568979271");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void deleteProductTest() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setName("아이스 카페 아메리카노 T");
        product.setPrice(4500L);
        product.setTemperatureOption("Ice");
        product.setCupOption("일회용컵");
        product.setSizeOption("tall");
        product.setImageurl("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        productService.createProduct(product);
        System.out.println("product1 = " + product);

        // when
        productService.deleteProduct(1L);

        // then
        assertThat(productService.getAllProducts()).isEmpty();
    }
}
