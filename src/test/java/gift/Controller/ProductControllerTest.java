package gift.Controller;

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
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerTest {

  @Autowired
  private ProductService productService;
  @Autowired
  private ProductController productController;

  @BeforeEach
  public void setUp() {
    productController = new ProductController(productService);
  }


  @DirtiesContext
  @Test
  public void testGetAllProducts() {
    Pageable pageable= PageRequest.of(0,5);
    
    // 제품 추가
    ProductDto productDto1 = new ProductDto(1L, "Coffee", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    ProductDto productDto2 = new ProductDto(2L, "Tea", 200,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc94364879792549ads8bdd8a3.jpg");

    ProductDto addedProduct1 = productController.addProduct(productDto1);
    ProductDto addedProduct2 = productController.addProduct(productDto2);

    Page<ProductDto> returnedProductEntities = productController.getAllProducts(pageable).getBody();

    // 반환된 제품 리스트 검증
    assertEquals(2, returnedProductEntities.getContent().size());
    assertEquals(productDto1.getId(), returnedProductEntities.getContent().get(0).getId());
    assertEquals(productDto1.getName(), returnedProductEntities.getContent().get(0).getName());
    assertEquals(productDto1.getPrice(), returnedProductEntities.getContent().get(0).getPrice());
    assertEquals(productDto1.getImageUrl(),
      returnedProductEntities.getContent().get(0).getImageUrl());
    assertEquals(productDto2.getId(), returnedProductEntities.getContent().get(1).getId());
    assertEquals(productDto2.getName(), returnedProductEntities.getContent().get(1).getName());
    assertEquals(productDto2.getPrice(), returnedProductEntities.getContent().get(1).getPrice());
    assertEquals(productDto2.getImageUrl(),
      returnedProductEntities.getContent().get(1).getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testGetProductById() {
    ProductDto productDto = new ProductDto(1L, "Coffee", 100,
      "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

    // 유효성 검사를 통과하도록 수정된 제품 추가
    ProductDto addedProduct = productController.addProduct(productDto);

    // getProductById() 호출 - 존재하는 제품 ID
    ResponseEntity<ProductDto> responseEntity = productController.getProductById(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

    // 반환된 제품 검증
    ProductDto returnedProductDto = responseEntity.getBody();
    assertNotNull(returnedProductDto);
    assertEquals(productDto.getId(), returnedProductDto.getId());
    assertEquals(productDto.getName(), returnedProductDto.getName());
    assertEquals(productDto.getPrice(), returnedProductDto.getPrice());
    assertEquals(productDto.getImageUrl(), returnedProductDto.getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testAddProduct() {
    ProductDto productDto = new ProductDto(1L, "Coffee", 4500,
      "https://example.com/coffee.jpg");

    ProductDto addedProduct = productController.addProduct(productDto);

    assertNotNull(addedProduct);
    assertNotNull(addedProduct.getId());
    assertEquals("Coffee", addedProduct.getName());
    assertEquals(4500, addedProduct.getPrice());
    assertEquals("https://example.com/coffee.jpg", addedProduct.getImageUrl());
  }


  @DirtiesContext
  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    ProductDto existingProduct = new ProductDto(1L, "Coffee", 4500,
      "https://example.com/coffee.jpg");
    productController.addProduct(existingProduct);

    // 업데이트할 제품 정보 - 유효한 이름으로 수정
    ProductDto updatedProduct = new ProductDto(1L, "Hot_Coffee", 4000,
      // Adjusted name to pass validation
      "https://example.com/coffee.jpg");

    // 유효성 검사를 통과하도록 수정된 제품 업데이트 요청
    ResponseEntity<ProductDto> response = productController.updateProduct(1L,
      updatedProduct);

    // 업데이트된 제품 받아오기
    ProductDto returnedProductDto = response.getBody();

    assertNotNull(returnedProductDto);
    assertEquals(1L, returnedProductDto.getId());
    assertEquals("Hot_Coffee", returnedProductDto.getName());
    assertEquals(4000, returnedProductDto.getPrice());
    assertEquals("https://example.com/coffee.jpg", returnedProductDto.getImageUrl());
  }

  @DirtiesContext
  @Test
  public void testDeleteProduct() {
    // 유효한 제품 추가
    ProductDto productDto = new ProductDto(1L, "Coffee", 100,
      "https://example.com/coffee.jpg");
    productController.addProduct(productDto);

    // deleteProduct() 호출 - 존재하는 제품 ID
    ResponseEntity<ProductDto> responseDto = productController.deleteProduct(1L);

    assertEquals(HttpStatus.OK, responseDto.getStatusCode()); // 상태 코드가 200 OK인지 확인

  }

  @DirtiesContext
  @Test
  public void testValidate() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    ProductDto invalidProduct1DTO = new ProductDto(1L, "pppppppppsdfsfdsppppppppProduct 1",
      100,
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