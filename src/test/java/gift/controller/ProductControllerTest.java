package gift.controller;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    public void 데이터_삭제() {
        productRepository.deleteAll();
    }

    @Test
    public void 상품_추가_성공() {
        ProductRequestDto productRequestDto = new ProductRequestDto("오둥이 입니다만", 29800, "https://example.com/product2.jpg");

        given()
                .contentType(ContentType.JSON)
                .body(productRequestDto)
                .when()
                .post("/api/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("오둥이 입니다만"))
                .body("price", equalTo(29800))
                .body("imageUrl", equalTo("https://example.com/product2.jpg"));
    }

    @Test
    public void 상품_수정_성공() {
        ProductResponseDto productResponseDto = productService.addProduct(new ProductRequestDto("오둥이 입니다만", 29800, "https://example.com/product2.jpg"));
        Long productId = productResponseDto.getId();

        ProductRequestDto updateDTO = new ProductRequestDto("오둥이 아닙니다만", 35000, "https://example.com/product3.jpg");

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when()
                .put("/api/products/{id}", productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("오둥이 아닙니다만"))
                .body("price", equalTo(35000))
                .body("imageUrl", equalTo("https://example.com/product3.jpg"));
    }

    @Test
    public void 모든_상품_조회_성공() {
        ProductResponseDto productResponseDto = productService.addProduct(new ProductRequestDto("오둥이 입니다만", 29800, "https://example.com/product2.jpg"));

        given()
                .when()
                .get("/api/products?page=0&size=10")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("products[0].id", equalTo(productResponseDto.getId().intValue()))
                .body("products[0].name", equalTo("오둥이 입니다만"))
                .body("products[0].price", equalTo(29800))
                .body("products[0].imageUrl", equalTo("https://example.com/product2.jpg"))
                .body("currentPage", equalTo(0))
                .body("totalPages", equalTo(1))
                .body("totalItems", equalTo(1));
    }

    @Test
    public void 상품_삭제_성공() {
        ProductResponseDto productResponseDto = productService.addProduct(new ProductRequestDto("오둥이 입니다만", 29800, "https://example.com/product2.jpg"));
        Long productId = productResponseDto.getId();

        given()
                .when()
                .delete("/api/products/{id}", productId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
