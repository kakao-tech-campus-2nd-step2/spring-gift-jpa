package gift.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.product.error.AlreadyExistsException;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS Products");
        jdbcTemplate.execute("CREATE TABLE Products (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "price BIGINT NOT NULL, " +
            "imageUrl VARCHAR(255))");
        jdbcTemplate.execute("TRUNCATE TABLE products");
    }

    @Test
    @DisplayName("상품 추가 기능 확인")
    void checkAddProductMethod() {
        //given
        Product tempProduct = new Product("아이스 아메리카노", 4500L, "https://image1.jpg");

        //when
        productService.addProduct(tempProduct);

        //then
        assertEquals(1L, productService.getProductById(1L).getId());
        assertEquals("아이스 아메리카노", productService.getProductById(1L).getName());
        assertEquals(4500L, productService.getProductById(1L).getPrice());
        assertEquals("https://image1.jpg", productService.getProductById(1L).getImageUrl());
    }

    @Test
    @DisplayName("상품 추가시 Exception 확인")
    void checkExceptionForAddProduct() {
        //given
        Product product = new Product("콜라", 1500L, "https://image1.jpg");

        Product invalidNameProduct = new Product(" ", 3000L, "https://image2.jpg");
        Product invalidPriceProduct = new Product("콜라", 0L, "https://image2.jpg");
        Product invalidImageUrlProduct = new Product("콜라", 3000L, "image2.jpg");

        //when & then
        productService.addProduct(product);
        assertThrows(AlreadyExistsException.class, () -> productService.addProduct(product));

        assertThrows(MethodArgumentNotValidException.class,
            () -> productService.addProduct(invalidNameProduct));
        assertThrows(MethodArgumentNotValidException.class,
            () -> productService.addProduct(invalidPriceProduct));
        assertThrows(MethodArgumentNotValidException.class,
            () -> productService.addProduct(invalidImageUrlProduct));
    }

    @Test
    @DisplayName("상품 전체 조회 기능 확인")
    void checkSelectAllProduct() {
        //given
        Product tempProduct1 = new Product("콜라", 1500L, "https://image1.jpg");
        Product tempProduct2 = new Product("레모네이드", 2500L, "https://image2.jpg");
        Product tempProduct3 = new Product("밀크쉐이크", 3000L, "https://image3.jpg");
        List<Product> tempList;

        //when
        productService.addProduct(tempProduct1);
        productService.addProduct(tempProduct2);
        productService.addProduct(tempProduct3);
        tempList = productService.getAllProducts();

        //then
        assertEquals(3, tempList.size());

        assertEquals("콜라", tempList.get(0).getName());
        assertEquals(1500L, tempList.get(0).getPrice());
        assertEquals("https://image1.jpg", tempList.get(0).getImageUrl());

        assertEquals("레모네이드", tempList.get(1).getName());
        assertEquals(2500L, tempList.get(1).getPrice());
        assertEquals("https://image2.jpg", tempList.get(1).getImageUrl());

        assertEquals("밀크쉐이크", tempList.get(2).getName());
        assertEquals(3000L, tempList.get(2).getPrice());
        assertEquals("https://image3.jpg", tempList.get(2).getImageUrl());
    }

    @Test
    @DisplayName("단일 상품 조회 기능 확인")
    void checkSelectProductById() {
        //given
        Product tempProduct1 = new Product("콜라", 1500L, "https://image1.jpg");
        Product tempProduct2 = new Product("레모네이드", 2500L, "https://image2.jpg");

        //when
        productService.addProduct(tempProduct1);
        productService.addProduct(tempProduct2);
        Product selectedProduct1 = productService.getProductById(1L);
        Product selectedProduct2 = productService.getProductById(2L);

        //then
        assertEquals("콜라", selectedProduct1.getName());
        assertEquals(1500L, selectedProduct1.getPrice());
        assertEquals("https://image1.jpg", selectedProduct1.getImageUrl());

        assertEquals("레모네이드", selectedProduct2.getName());
        assertEquals(2500L, selectedProduct2.getPrice());
        assertEquals("https://image2.jpg", selectedProduct2.getImageUrl());
    }

    @Test
    @DisplayName("상품 삭제 기능 확인")
    void checkDeleteProduct() {
        //given
        Product tempProduct1 = new Product("콜라", 1500L, "https://image1.jpg");

        //when
        productService.addProduct(tempProduct1);
        productService.deleteProduct(1L);

        //then
        assertEquals(0, productService.getAllProducts().size());
    }

    @Test
    @DisplayName("상품 수정 기능 확인")
    void checkUpdateProduct() {
        //given
        Product tempProduct1 = new Product("콜라", 1500L, "https://image1.jpg");
        Product tempProduct2 = new Product("아이스크림", 1000L, "https://image2.jpg");
        Product tempProduct3 = new Product("레모네이드", 2000L, "https://image3.jpg");

        //when
        productService.addProduct(tempProduct1);
        productService.updateProduct(1L, tempProduct2);

        //then
        assertEquals("아이스크림", productService.getProductById(1L).getName());
        assertEquals(1000L, productService.getProductById(1L).getPrice());
        assertEquals("https://image2.jpg", productService.getProductById(1L).getImageUrl());
        assertEquals(1, productService.getAllProducts().size());
    }

    @Test
    @DisplayName("상품 수정시 Exception 확인")
    void checkExceptionForUpdateProduct() {
        //given
        Product product = new Product("콜라", 1500L, "https://image1.jpg");
        Product invalidNameProduct = new Product(" ", 3000L, "https://image2.jpg");
        Product invalidPriceProduct = new Product("콜라", 0L, "https://image2.jpg");
        Product invalidImageUrlProduct = new Product("콜라", 3000L, "image2.jpg");

        //when & then
        productService.addProduct(product);
        assertThrows(AlreadyExistsException.class, () -> productService.updateProduct(1L, product));

        assertThrows(MethodArgumentNotValidException.class,
            () -> productService.updateProduct(1L, invalidNameProduct));
        assertThrows(MethodArgumentNotValidException.class,
            () -> productService.updateProduct(1L, invalidPriceProduct));
        assertThrows(MethodArgumentNotValidException.class,
            () -> productService.updateProduct(1L, invalidImageUrlProduct));
    }

}
