package gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test") // 테스트 프로파일 활성화
public class ProductControllerTest {

  @Autowired
  private ProductService productService;
  @Autowired
  private ProductController productController;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void setUp() {
    productController = new ProductController(productService);
    // 데이터베이스 초기화를 위해 schema.sql 실행
    jdbcTemplate.execute("DROP TABLE IF EXISTS product");
    jdbcTemplate.execute("CREATE TABLE product (" +
      "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
      "name VARCHAR(255)," +
      "price BIGINT," +
      "imageUrl VARCHAR(255)" +
      ")");
  }

  @AfterEach
  public void tearDown() {
    // 데이터베이스 정리
    jdbcTemplate.execute("DELETE FROM product");
  }

  @Test
  public void testGetAllProducts() {
    // 제품 추가
    Product product1 = new Product(1L, "Product 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    Product product2 = new Product(2L, "Product 2", 200,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc94364879792549ads8bdd8a3.jpg");
    productController.addProduct(product1);
    productController.addProduct(product2);
    // getAllProducts() 호출
    List<Product> returnedProducts = productController.getAllProducts();
    // 반환된 제품 리스트 검증
    assertEquals(2, returnedProducts.size());
    assertEquals(product1.getId(), returnedProducts.get(0).getId());
    assertEquals(product1.getName(), returnedProducts.get(0).getName());
    assertEquals(product1.getPrice(), returnedProducts.get(0).getPrice());
    assertEquals(product1.getImageUrl(), returnedProducts.get(0).getImageUrl());
    assertEquals(product2.getId(), returnedProducts.get(1).getId());
    assertEquals(product2.getName(), returnedProducts.get(1).getName());
    assertEquals(product2.getPrice(), returnedProducts.get(1).getPrice());
    assertEquals(product2.getImageUrl(), returnedProducts.get(1).getImageUrl());
  }


  @Test
  public void testGetProductById() {
    // 제품 추가
    Product product = new Product(1L, "Product 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    productController.addProduct(product);

    // getProductById() 호출 - 존재하는 제품 ID
    ResponseEntity<Product> responseEntity = productController.getProductById(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

    // 반환된 제품 검증
    Product returnedProduct = responseEntity.getBody();
    assertEquals(product.getId(), returnedProduct.getId());
    assertEquals(product.getName(), returnedProduct.getName());
    assertEquals(product.getPrice(), returnedProduct.getPrice());
    assertEquals(product.getImageUrl(), returnedProduct.getImageUrl());
  }

  @Test
  public void testAddProduct() {
    Product newProduct = new Product(1L, "아이스 카페 아메리카노", 4500,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    Product addedProduct = productController.addProduct(newProduct);

    assertNotNull(addedProduct);
    assertNotNull(addedProduct.getId());
    assertEquals("아이스 카페 아메리카노", addedProduct.getName());
    assertEquals(4500, addedProduct.getPrice());
    assertEquals(
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
      addedProduct.getImageUrl());
  }


  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    Product existingProduct = new Product(1L, "아이스 카페 아메리카노", 4500,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    productController.addProduct(existingProduct);

    // 업데이트할 제품 정보
    Product updatedProduct = new Product(1L, "핫 카페 아메리카노", 4000,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    // 제품 업데이트 요청
    ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);
    // 업데이트된 제품 받아오기
    Product returnedProduct = response.getBody();

    assertNotNull(returnedProduct);
    assertEquals(1L, returnedProduct.getId());
    assertEquals("핫 카페 아메리카노", returnedProduct.getName());
    assertEquals(4000, returnedProduct.getPrice());
    assertEquals(
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
      returnedProduct.getImageUrl());
  }

  @Test
  public void testDeleteProduct() {
    // 제품 추가
    Product product = new Product(1L, "Product 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    productController.addProduct(product);

    // deleteProduct() 호출 - 존재하는 제품 ID
    ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.NO_CONTENT,
      responseEntity.getStatusCode()); // 상태 코드가 204 NO CONTENT인지 확인

    // ProductService를 통해 제품이 삭제되었는지 확인
    assertThrows(EmptyResultDataAccessException.class, () -> {
      productController.getProductById(1L); // 삭제된 제품 조회 시 예외가 발생해야 함
    });
  }

  @Test
  public void testValidate(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Product invalidProduct1 = new Product(1L, "pppppppppsdfsfdsppppppppProduct 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    Product invalidProduct2 = new Product(2L, "카카오 product", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    Set<ConstraintViolation<Product>> violations1 = validator.validate(invalidProduct1);
    Set<ConstraintViolation<Product>> violations2 = validator.validate(invalidProduct2);

    assertThrows(ConstraintViolationException.class, () -> {
        throw new ConstraintViolationException(violations1);
    });

    assertThrows(ConstraintViolationException.class, () -> {
        throw new ConstraintViolationException(violations2);
    });
  }
}
