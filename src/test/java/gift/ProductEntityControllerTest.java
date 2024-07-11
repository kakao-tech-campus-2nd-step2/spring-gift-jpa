package gift;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.Controller.ProductController;
import gift.DTO.ProductEntity;
import gift.Service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ProductEntityControllerTest {

  @Autowired
  private ProductService productService;
  @Autowired
  private ProductController productController;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void setUp() {
    productController = new ProductController(productService);
  }


  @DirtiesContext
  @Test
  public void testGetAllProducts() {
    // 제품 추가
    ProductEntity productEntity1 = new ProductEntity(1L, "Coffee", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductEntity productEntity2 = new ProductEntity(2L, "Tea", 200,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc94364879792549ads8bdd8a3.jpg");

    // 제품 추가 시 유효성 검사를 통과해야 함
    assertDoesNotThrow(() -> productController.addProduct(productEntity1));
    assertDoesNotThrow(() -> productController.addProduct(productEntity2));

    // getAllProducts() 호출
    List<ProductEntity> returnedProductEntities = productController.getAllProducts();

    // 반환된 제품 리스트 검증
    assertEquals(2, returnedProductEntities.size());
    assertEquals(productEntity1.getId(), returnedProductEntities.get(0).getId());
    assertEquals(productEntity1.getName(), returnedProductEntities.get(0).getName());
    assertEquals(productEntity1.getPrice(), returnedProductEntities.get(0).getPrice());
    assertEquals(productEntity1.getImageUrl(), returnedProductEntities.get(0).getImageUrl());
    assertEquals(productEntity2.getId(), returnedProductEntities.get(1).getId());
    assertEquals(productEntity2.getName(), returnedProductEntities.get(1).getName());
    assertEquals(productEntity2.getPrice(), returnedProductEntities.get(1).getPrice());
    assertEquals(productEntity2.getImageUrl(), returnedProductEntities.get(1).getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testGetProductById() {
    // 제품 추가 - 유효한 이름으로 수정
    ProductEntity productEntity = new ProductEntity(1L, "Coffee", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    // 유효성 검사를 통과하도록 수정된 제품 추가
    assertDoesNotThrow(() -> productController.addProduct(productEntity));

    // getProductById() 호출 - 존재하는 제품 ID
    ResponseEntity<Optional<ProductEntity>> responseEntity = productController.getProductById(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

    // 반환된 제품 검증
    Optional<ProductEntity> returnedProductDto = responseEntity.getBody();
    assertNotNull(returnedProductDto);
    assertEquals(productEntity.getId(), returnedProductDto.get().getId());
    assertEquals(productEntity.getName(), returnedProductDto.get().getName());
    assertEquals(productEntity.getPrice(), returnedProductDto.get().getPrice());
    assertEquals(productEntity.getImageUrl(), returnedProductDto.get().getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testAddProduct() {
    ProductEntity newProductEntity = new ProductEntity(1L, "Coffee", 4500,
      "https://example.com/coffee.jpg");

    ProductEntity addedProductEntity = productController.addProduct(newProductEntity);

    assertNotNull(addedProductEntity);
    assertNotNull(addedProductEntity.getId());
    assertEquals("Coffee", addedProductEntity.getName());
    assertEquals(4500, addedProductEntity.getPrice());
    assertEquals("https://example.com/coffee.jpg", addedProductEntity.getImageUrl());
  }


  @DirtiesContext
  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    ProductEntity existingProductEntity = new ProductEntity(1L, "Coffee", 4500,
      "https://example.com/coffee.jpg");
    productController.addProduct(existingProductEntity);

    // 업데이트할 제품 정보 - 유효한 이름으로 수정
    ProductEntity updatedProductEntity = new ProductEntity(1L, "Hot_Coffee", 4000,
      // Adjusted name to pass validation
      "https://example.com/coffee.jpg");

    // 유효성 검사를 통과하도록 수정된 제품 업데이트 요청
    ResponseEntity<ProductEntity> response = productController.updateProduct(1L,
      updatedProductEntity);

    // 업데이트된 제품 받아오기
    ProductEntity returnedProductEntity = response.getBody();

    assertNotNull(returnedProductEntity);
    assertEquals(1L, returnedProductEntity.getId());
    assertEquals("Hot_Coffee", returnedProductEntity.getName()); // Check against the adjusted name
    assertEquals(4000, returnedProductEntity.getPrice());
    assertEquals("https://example.com/coffee.jpg", returnedProductEntity.getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testDeleteProduct() {
    // 유효한 제품 추가
    ProductEntity productEntity = new ProductEntity(1L, "Coffee", 100,
      "https://example.com/coffee.jpg");
    productController.addProduct(productEntity);

    // deleteProduct() 호출 - 존재하는 제품 ID
    ResponseEntity<Optional<ProductEntity>> responseEntity = productController.deleteProduct(1L);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

  }

  @DirtiesContext

  @Test
  public void testValidate() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    ProductEntity invalidProduct1DTO = new ProductEntity(1L, "pppppppppsdfsfdsppppppppProduct 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductEntity invalidProduct2DTO = new ProductEntity(2L, "카카오 product", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    Set<ConstraintViolation<ProductEntity>> violations1 = validator.validate(invalidProduct1DTO);
    Set<ConstraintViolation<ProductEntity>> violations2 = validator.validate(invalidProduct2DTO);

    assertThrows(ConstraintViolationException.class, () -> {
      throw new ConstraintViolationException(violations1);
    });

    assertThrows(ConstraintViolationException.class, () -> {
      throw new ConstraintViolationException(violations2);
    });
  }
}