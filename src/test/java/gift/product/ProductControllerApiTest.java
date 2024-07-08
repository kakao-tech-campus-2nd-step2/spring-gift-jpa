package gift.product;

import gift.product.persistence.entity.Product;
import gift.product.presentation.dto.RequestProductDto;
import gift.product.presentation.dto.ResponseProductDto;
import gift.global.exception.NotFoundException;
import gift.product.persistence.repository.ProductRepository;
import java.util.Objects;
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

    @Test
    void testCreateProduct() {
        // given
        String name = "테스트 상품_()[]+-";
        Integer price = 1000;
        String description = "테스트 상품 설명";
        String imageUrl = "http://test.com";

        RequestProductDto requestProductDto = new RequestProductDto(name, price, description, imageUrl);
        String url = "http://localhost:"+port+"/products";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestProductDto, Long.class);

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
        String url = "http://localhost:"+port+"/products";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestProductDto, Long.class);
        Long id = responseEntity.getBody();

        // when
        String getUrl = "http://localhost:"+port+"/products/"+id;
        ResponseEntity<ResponseProductDto> getResponseEntity = restTemplate.getForEntity(getUrl, ResponseProductDto.class);

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
        String url = "http://localhost:"+port+"/products";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestProductDto, Long.class);
        Long id = responseEntity.getBody();

        String updatedName = "수정된 상품";
        Integer updatedPrice = 2000;
        String updatedDescription = "수정된 상품 설명";
        String updatedImageUrl = "http://updated.com";

        RequestProductDto updateProductDto = new RequestProductDto(updatedName, updatedPrice, updatedDescription, updatedImageUrl);
        String updateUrl = "http://localhost:"+port+"/products/"+id;

        HttpEntity<RequestProductDto> requestEntity = new HttpEntity<>(updateProductDto);

        // when
        ResponseEntity<Long> updateResponseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, requestEntity, Long.class);


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
        String url = "http://localhost:" + port + "/products";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestProductDto, Long.class);
        Long id = responseEntity.getBody();

        String deleteUrl = "http://localhost:" + port + "/products/" + id;

        // when
        restTemplate.delete(deleteUrl);

        // then
        assertThrows(NotFoundException.class, () -> productRepository.getProductById(id));
    }

    @Test
    void testCreateProduct_Failure() {
        // given
        String url = "http://localhost:" + port + "/products";

        String requestJson = """
                {
                    "name": null,
                    "price": 1000,
                    "description": "테스트 상품 설명",
                    "imageUrl": "http://test.com"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

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

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
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

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
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

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity<>(requestJson, headers);

        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        assertThat(Objects.requireNonNull(responseEntity.getBody()).contains("imageUrl")).isTrue();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        String getUrl = "http://localhost:" + port + "/products/" + nonExistentId;

        // when
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        RequestProductDto updateProductDto = new RequestProductDto("수정된 상품", 2000, "수정된 상품 설명", "http://updated.com");
        String updateUrl = "http://localhost:" + port + "/products/" + nonExistentId;

        HttpEntity<RequestProductDto> requestEntity = new HttpEntity<>(updateProductDto);

        // when
        ResponseEntity<String> updateResponseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, requestEntity, String.class);

        // then
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        String deleteUrl = "http://localhost:" + port + "/products/" + nonExistentId;

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
