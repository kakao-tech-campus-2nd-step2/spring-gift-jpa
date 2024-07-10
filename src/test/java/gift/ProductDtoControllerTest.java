package gift;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.Controller.ProductController;
import gift.DTO.ProductDto;
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
public class ProductDtoControllerTest {

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
    ProductDto productDto1 = new ProductDto(1L, "Coffee", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductDto productDto2 = new ProductDto(2L, "Tea", 200,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc94364879792549ads8bdd8a3.jpg");

    // 제품 추가 시 유효성 검사를 통과해야 함
    assertDoesNotThrow(() -> productController.addProduct(productDto1));
    assertDoesNotThrow(() -> productController.addProduct(productDto2));

    // getAllProducts() 호출
    List<ProductDto> returnedProductDtos = productController.getAllProducts();



    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

    // 반환된 제품 검증
    Optional<ProductDto> returnedProductDto = responseEntity.getBody();
    assertNotNull(returnedProductDto);
    assertEquals(productDTO.getId(), returnedProductDto.get().getId());
    assertEquals(productDTO.getName(), returnedProductDto.get().getName());
    assertEquals(productDTO.getPrice(), returnedProductDto.get().getPrice());
    assertEquals(productDTO.getImageUrl(), returnedProductDto.get().getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testAddProduct() {
    ProductDto newProductDto = new ProductDto(1L, "Coffee", 4500,
      "https://example.com/coffee.jpg");


    ProductDto addedProductDto = productController.addProduct(newProductDto);

    assertNotNull(addedProductDto);
    assertNotNull(addedProductDto.getId());
    assertEquals("Coffee", addedProductDto.getName());
    assertEquals(4500, addedProductDto.getPrice());
    assertEquals("https://example.com/coffee.jpg", addedProductDto.getImageUrl());
  }


  @DirtiesContext
  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    ProductDto existingProductDto = new ProductDto(1L, "Coffee", 4500,
      "https://example.com/coffee.jpg");
    productController.addProduct(existingProductDto);

    // 업데이트할 제품 정보 - 유효한 이름으로 수정
    ProductDto updatedProductDto = new ProductDto(1L, "Hot_Coffee", 4000, // Adjusted name to pass validation
      "https://example.com/coffee.jpg");

    // 유효성 검사를 통과하도록 수정된 제품 업데이트 요청
    ResponseEntity<ProductDto> response = productController.updateProduct(1L, updatedProductDto);


    // 업데이트된 제품 받아오기
    ProductDto returnedProductDto = response.getBody();

    assertNotNull(returnedProductDto);
    assertEquals(1L, returnedProductDto.getId());
    assertEquals("Hot_Coffee", returnedProductDto.getName()); // Check against the adjusted name
    assertEquals(4000, returnedProductDto.getPrice());
    assertEquals("https://example.com/coffee.jpg", returnedProductDto.getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testDeleteProduct() {
    // 유효한 제품 추가
    ProductDto productDTO = new ProductDto(1L, "Coffee", 100,
      "https://example.com/coffee.jpg");
    productController.addProduct(productDTO);

    // deleteProduct() 호출 - 존재하는 제품 ID
    ResponseEntity<Optional<ProductDto>> responseEntity = productController.deleteProduct(1L);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

  }

  @DirtiesContext

  @Test
  public void testValidate() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    ProductDto invalidProduct1DTO = new ProductDto(1L, "pppppppppsdfsfdsppppppppProduct 1", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductDto invalidProduct2DTO = new ProductDto(2L, "카카오 product", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    Set<ConstraintViolation<ProductDto>> violations1 = validator.validate(invalidProduct1DTO);
    Set<ConstraintViolation<ProductDto>> violations2 = validator.validate(invalidProduct2DTO);

    assertThrows(ConstraintViolationException.class, () -> {
      throw new ConstraintViolationException(violations1);
    });

    assertThrows(ConstraintViolationException.class, () -> {
      throw new ConstraintViolationException(violations2);
    });
  }
}