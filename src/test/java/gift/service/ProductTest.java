package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.auth.Login;
import gift.domain.Product.CreateProduct;
import gift.domain.Product.UpdateProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductTest {

    @LocalServerPort
    private int port;

    private String url = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;


    @BeforeEach
    public void setUp() {
        Login login = new Login("kakao1@kakao.com", "1234");

        HttpEntity<Login> requestEntity = new HttpEntity<>(login);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/login", POST,
            requestEntity, String.class);

        int startIndex = responseEntity.getBody().indexOf("\"token\":\"") + "\"token\":\"".length();

        int endIndex = responseEntity.getBody().indexOf("\"", startIndex);

        token = responseEntity.getBody().substring(startIndex, endIndex);

    }

    @Test
    @DisplayName("상품 생성")
    public void CreateProduct() {
        CreateProduct body = new CreateProduct("test1", 1000, "test1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/products", POST,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("상품 수정(유저 없음)")
    public void NotFoundUpdateProduct() {
        UpdateProduct body = new UpdateProduct("test2", 1000, "test1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/products/9999", PUT,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("상품 수정")
    public void UpdateProduct() {
        UpdateProduct body = new UpdateProduct("test2", 1000, "test1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/products/1", PUT,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("상품 삭제(유저 없음)")
    public void NotFoundRemoveProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/products/9999",
            DELETE, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("상품 삭제")
    public void removeProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/products/1",
            DELETE, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

}