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
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
    public void 상품_추가() {
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
    public void 상품_수정() {
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
    public void 모든_상품_조회() {
        ProductResponseDto productResponseDto = productService.addProduct(new ProductRequestDto("오둥이 입니다만", 29800, "https://example.com/product2.jpg"));

        given()
        .when()
            .get("/api/products")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("[0].id", equalTo(productResponseDto.getId().intValue()))
            .body("[0].name", equalTo("오둥이 입니다만"))
            .body("[0].price", equalTo(29800))
            .body("[0].imageUrl", equalTo("https://example.com/product2.jpg"));
    }

    @Test
    public void 상품_삭제() {
        ProductResponseDto productResponseDto = productService.addProduct(new ProductRequestDto("오둥이 입니다만", 29800, "https://example.com/product2.jpg"));
        Long productId = productResponseDto.getId();

        given()
        .when()
            .delete("/api/products/{id}", productId)
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

    }
}
