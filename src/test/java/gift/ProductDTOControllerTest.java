package gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.Controller.ProductController;
import gift.DTO.ProductDTO;
import gift.Service.ProductService;
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
public class ProductDTOControllerTest {

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
    ProductDTO productDTO1 = new ProductDTO(1L, "Product 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductDTO productDTO2 = new ProductDTO(2L, "Product 2", 200,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc94364879792549ads8bdd8a3.jpg");
    productController.addProduct(productDTO1);
    productController.addProduct(productDTO2);
    // getAllProducts() 호출
    List<ProductDTO> returnedProductDTOS = productController.getAllProducts();
    // 반환된 제품 리스트 검증
    assertEquals(2, returnedProductDTOS.size());
    assertEquals(productDTO1.getId(), returnedProductDTOS.get(0).getId());
    assertEquals(productDTO1.getName(), returnedProductDTOS.get(0).getName());
    assertEquals(productDTO1.getPrice(), returnedProductDTOS.get(0).getPrice());
    assertEquals(productDTO1.getImageUrl(), returnedProductDTOS.get(0).getImageUrl());
    assertEquals(productDTO2.getId(), returnedProductDTOS.get(1).getId());
    assertEquals(productDTO2.getName(), returnedProductDTOS.get(1).getName());
    assertEquals(productDTO2.getPrice(), returnedProductDTOS.get(1).getPrice());
    assertEquals(productDTO2.getImageUrl(), returnedProductDTOS.get(1).getImageUrl());
  }


  @Test
  public void testGetProductById() {
    // 제품 추가
    ProductDTO productDTO = new ProductDTO(1L, "Product 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    productController.addProduct(productDTO);

    // getProductById() 호출 - 존재하는 제품 ID
    ResponseEntity<ProductDTO> responseEntity = productController.getProductById(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

    // 반환된 제품 검증
    ProductDTO returnedProductDTO = responseEntity.getBody();
    assertEquals(productDTO.getId(), returnedProductDTO.getId());
    assertEquals(productDTO.getName(), returnedProductDTO.getName());
    assertEquals(productDTO.getPrice(), returnedProductDTO.getPrice());
    assertEquals(productDTO.getImageUrl(), returnedProductDTO.getImageUrl());
  }

  @Test
  public void testAddProduct() {
    ProductDTO newProductDTO = new ProductDTO(1L, "아이스 카페 아메리카노", 4500,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    ProductDTO addedProductDTO = productController.addProduct(newProductDTO);

    assertNotNull(addedProductDTO);
    assertNotNull(addedProductDTO.getId());
    assertEquals("아이스 카페 아메리카노", addedProductDTO.getName());
    assertEquals(4500, addedProductDTO.getPrice());
    assertEquals(
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
      addedProductDTO.getImageUrl());
  }


  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    ProductDTO existingProductDTO = new ProductDTO(1L, "아이스 카페 아메리카노", 4500,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    productController.addProduct(existingProductDTO);

    // 업데이트할 제품 정보
    ProductDTO updatedProductDTO = new ProductDTO(1L, "핫 카페 아메리카노", 4000,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    // 제품 업데이트 요청
    ResponseEntity<ProductDTO> response = productController.updateProduct(1L, updatedProductDTO);
    // 업데이트된 제품 받아오기
    ProductDTO returnedProductDTO = response.getBody();

    assertNotNull(returnedProductDTO);
    assertEquals(1L, returnedProductDTO.getId());
    assertEquals("핫 카페 아메리카노", returnedProductDTO.getName());
    assertEquals(4000, returnedProductDTO.getPrice());
    assertEquals(
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
      returnedProductDTO.getImageUrl());
  }

  @Test
  public void testDeleteProduct() {
    // 제품 추가
    ProductDTO productDTO = new ProductDTO(1L, "Product 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    productController.addProduct(productDTO);

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

    ProductDTO invalidProduct1DTO = new ProductDTO(1L, "pppppppppsdfsfdsppppppppProduct 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductDTO invalidProduct2DTO = new ProductDTO(2L, "카카오 product", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    Set<ConstraintViolation<ProductDTO>> violations1 = validator.validate(invalidProduct1DTO);
    Set<ConstraintViolation<ProductDTO>> violations2 = validator.validate(invalidProduct2DTO);

    assertThrows(ConstraintViolationException.class, () -> {
        throw new ConstraintViolationException(violations1);
    });

    assertThrows(ConstraintViolationException.class, () -> {
        throw new ConstraintViolationException(violations2);
    });
  }
}
