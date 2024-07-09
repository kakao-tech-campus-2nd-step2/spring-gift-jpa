package gift.product;

import gift.member.business.dto.JwtToken;
import gift.member.presentation.dto.RequestMemberDto;
import gift.product.persistence.entity.Product;
import gift.product.presentation.dto.RequestProductDto;
import gift.product.presentation.dto.RequestProductIdsDto;
import gift.product.presentation.dto.ResponseProductDto;
import gift.global.exception.NotFoundException;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    private static String accessToken;

    private static HttpHeaders headers;

    @BeforeAll
    static void setUp(@Autowired TestRestTemplate restTemplate,
        @LocalServerPort int port
    ) {
        RequestMemberDto requestMemberDto = new RequestMemberDto(
            "test@example.com",
            "test");

        String url = "http://localhost:" + port + "/api/members/register";
        var response = restTemplate.postForEntity(url, requestMemberDto, JwtToken.class);
        var jwtToken = response.getBody();
        accessToken = jwtToken.accessToken();

        headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void testCreateProduct() {
        // given
        String name = "테스트 상품_()[]+-";
        Integer price = 1000;
        String description = "테스트 상품 설명";
        String imageUrl = "http://test.com";

        RequestProductDto requestProductDto = new RequestProductDto(name, price, description, imageUrl);
        String url = "http://localhost:"+port+"/api/products";

        var entity = new HttpEntity<>(requestProductDto, headers);

        // when
        var responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        Long id = responseEntity.getBody();

        Product product = productRepository.getProductById(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getUrl()).isEqualTo(imageUrl);

        productRepository.deleteProductById(id);
    }

    @Test
    void testGetProduct(){
        // given
        String name = "테스트 상품";
        Integer price = 1000;
        String description = "테스트 상품 설명";
        String imageUrl = "http://test.com";

        RequestProductDto requestProductDto = new RequestProductDto(name, price, description, imageUrl);
        String url = "http://localhost:"+port+"/api/products";

        var entity = new HttpEntity<>(requestProductDto, headers);

        var responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class);

        Long id = responseEntity.getBody();

        // when
        String getUrl = "http://localhost:"+port+"/api/products/"+id;

        entity = new HttpEntity<>(headers);

        var getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseProductDto.class);


        // then
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody()).isNotNull();
        ResponseProductDto responseProductDto = getResponseEntity.getBody();
        assertThat(responseProductDto.name()).isEqualTo(name);
        assertThat(responseProductDto.price()).isEqualTo(price);
        assertThat(responseProductDto.description()).isEqualTo(description);
        assertThat(responseProductDto.imageUrl()).isEqualTo(imageUrl);

        productRepository.deleteProductById(id);
    }

    @Test
    void testUpdateProduct(){
        // given
        String name = "테스트 상품";
        Integer price = 1000;
        String description = "테스트 상품 설명";
        String imageUrl = "http://test.com";

        RequestProductDto requestProductDto = new RequestProductDto(name, price, description, imageUrl);
        String url = "http://localhost:"+port+"/api/products";

        var entity = new HttpEntity<>(requestProductDto, headers);

        var responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class);

        Long id = responseEntity.getBody();

        String updatedName = "수정된 상품";
        Integer updatedPrice = 2000;
        String updatedDescription = "수정된 상품 설명";
        String updatedImageUrl = "http://updated.com";

        RequestProductDto updateProductDto = new RequestProductDto(updatedName, updatedPrice, updatedDescription, updatedImageUrl);
        String updateUrl = "http://localhost:"+port+"/api/products/"+id;

        entity = new HttpEntity<>(updateProductDto, headers);

        // when
        var updateResponseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, entity, Long.class);


        // then
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponseEntity.getBody()).isNotNull();
        Long updatedId = updateResponseEntity.getBody();

        Product product = productRepository.getProductById(updatedId);
        assertThat(product.getName()).isEqualTo(updatedName);
        assertThat(product.getPrice()).isEqualTo(updatedPrice);
        assertThat(product.getDescription()).isEqualTo(updatedDescription);
        assertThat(product.getUrl()).isEqualTo(updatedImageUrl);

        productRepository.deleteProductById(updatedId);
    }

    @Test
    void testDeleteProduct() {
        // given
        String name = "테스트 상품";
        Integer price = 1000;
        String description = "테스트 상품 설명";
        String imageUrl = "http://test.com";

        RequestProductDto requestProductDto = new RequestProductDto(name, price, description, imageUrl);
        String url = "http://localhost:" + port + "/api/products";

        var entity = new HttpEntity<>(requestProductDto, headers);

        var responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class);

        Long id = responseEntity.getBody();

        String deleteUrl = "http://localhost:" + port + "/api/products";

        RequestProductIdsDto requestProductIdsDto = new RequestProductIdsDto(List.of(id));
        var deleteEntity = new HttpEntity<>(requestProductIdsDto, headers);

        // when
        var deleteResponseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, deleteEntity, Void.class);

        // then
        assertThrows(NotFoundException.class, () -> productRepository.getProductById(id));
    }

    @Test
    void testCreateProduct_Failure() {
        // given
        String url = "http://localhost:" + port + "/api/products";

        String requestJson = """
                {
                    "name": null,
                    "price": 1000,
                    "description": "테스트 상품 설명",
                    "imageUrl": "http://test.com"
                }
                """;

        var requestEntity = new HttpEntity<>(requestJson, headers);

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // 특수문자
        requestJson = """
                {
                    "name": "#@",
                    "price": 1000,
                    "description": "테스트 상품 설명",
                    "imageUrl": "http://test.com"
                }
                """;

        requestEntity = new HttpEntity<>(requestJson, headers);

        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // 카카오 검증
        requestJson = """
                {
                    "name": "카카오",
                    "price": 1000,
                    "description": "테스트 상품 설명",
                    "imageUrl": "http://test.com"
                }
                """;

        requestEntity = new HttpEntity<>(requestJson, headers);

        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        requestJson = """
                {
                    "name": "asdf",
                    "price": 1000,
                    "description": "테스트 상품 설명",
                    "imageUrl": "url 형식 아님"
                }
                """;

        requestEntity = new HttpEntity<>(requestJson, headers);

        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        assertThat(Objects.requireNonNull(responseEntity.getBody()).contains("imageUrl")).isTrue();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        String getUrl = "http://localhost:" + port + "/api/products/" + nonExistentId;

        var entity = new HttpEntity<>(headers);

        // when
        var responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        RequestProductDto updateProductDto = new RequestProductDto("수정된 상품", 2000, "수정된 상품 설명", "http://updated.com");
        String updateUrl = "http://localhost:" + port + "/api/products/" + nonExistentId;
        var entity = new HttpEntity<>(updateProductDto, headers);

        // when
        ResponseEntity<String> updateResponseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, entity, String.class);

        // then
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        String deleteUrl = "http://localhost:" + port + "/api/products/" + nonExistentId;

        var entity = new HttpEntity<>(headers);

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
