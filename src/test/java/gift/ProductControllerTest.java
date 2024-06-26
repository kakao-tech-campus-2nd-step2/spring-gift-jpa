package gift;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerTest {
  private ProductController productController;

  @BeforeEach
  public void setUp() {
    productController = new ProductController();
  }
  @Test
  public void testGetAllProducts() {
    // 제품 추가
    Product product1 = new Product(1L, "Product 1", 100);
    Product product2 = new Product(2L, "Product 2", 200);
    productController.addProduct(product1);
    productController.addProduct(product2);

    // getAllProducts() 호출
    List<Product> returnedProducts = productController.getAllProducts();

    // 반환된 제품 리스트 검증
    assertEquals(2, returnedProducts.size());
    assertEquals(product1.getId(), returnedProducts.get(0).getId());
    assertEquals(product1.getName(), returnedProducts.get(0).getName());
    assertEquals(product1.getPrice(), returnedProducts.get(0).getPrice());
    assertEquals(product2.getId(), returnedProducts.get(1).getId());
    assertEquals(product2.getName(), returnedProducts.get(1).getName());
    assertEquals(product2.getPrice(), returnedProducts.get(1).getPrice());
  }

  @Test
  public void testGetProductById() {
    // 제품 추가
    Product product = new Product(1L, "Product 1", 100);
    productController.addProduct(product);

    // getProductById() 호출 - 존재하는 제품 ID
    ResponseEntity<Product> responseEntity = productController.getProductById(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인
    assertTrue(responseEntity.hasBody()); // 반환된 ResponseEntity가 body를 포함하는지 확인

    // 반환된 제품 검증
    Product returnedProduct = responseEntity.getBody();
    assertEquals(product.getId(), returnedProduct.getId());
    assertEquals(product.getName(), returnedProduct.getName());
    assertEquals(product.getPrice(), returnedProduct.getPrice());
  }

  @Test
  public void testAddProduct() {
    Product newProduct = new Product(1L, "아이스 카페 아메리카노 T", 4500);

    Product addedProduct = productController.addProduct(newProduct);

    assertNotNull(addedProduct);
    assertNotNull(addedProduct.getId());
    assertEquals("아이스 카페 아메리카노 T", addedProduct.getName());
    assertEquals(4500, addedProduct.getPrice());
  }

  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    Product existingProduct = new Product(1L, "아이스 카페 아메리카노 T", 4500);
    productController.addProduct(existingProduct);

    // 업데이트할 제품 정보
    Product updatedProduct = new Product(1L, "핫 카페 아메리카노 T", 4000);
    // 제품 업데이트 요청
    ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);
    // 업데이트된 제품 받아오기
    Product returnedProduct = response.getBody();

    assertNotNull(returnedProduct);
    assertEquals(1L, returnedProduct.getId());
    assertEquals("핫 카페 아메리카노 T", returnedProduct.getName());
    assertEquals(4000, returnedProduct.getPrice());
  }

  @Test
  public void testDeleteProduct() {
    // 제품 추가
    Product product = new Product(1L, "Product 1", 100);
    productController.addProduct(product);

    // deleteProduct() 호출 - 존재하는 제품 ID
    ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // 상태 코드가 204 NO CONTENT인지 확인

    // ProductService를 통해 제품이 삭제되었는지 확인
    ResponseEntity<Product> response = productController.getProductById(1L);
  }
}