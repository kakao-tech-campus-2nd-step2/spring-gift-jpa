package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import gift.auth.Login;
import gift.domain.Product.CreateProduct;
import gift.domain.Product.UpdateProduct;
import gift.domain.Wish.createWish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WishTest {

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
    @DisplayName("위시리스트 조회(위시리스트 없음)")
    public void NotFoundGetWish() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/wish/9999", GET,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("위시리스트 생성")
    public void CreateWish() {
        createWish body = new createWish(3L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/wish", POST,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("위시리스트 생성(상품 없음)")
    public void NotFoundCreateWish() {
        createWish body = new createWish(100L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/wish", POST,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("위시리스트 생성(이미 존재)")
    public void AlreadyCreateWish() {
        createWish body = new createWish(2L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/wish", POST,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(ALREADY_REPORTED);
    }

    @Test
    @DisplayName("위시리스트 삭제")
    public void removeWish() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/wish/1",
            DELETE, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("위시리스트 삭제(위시 리스트에 없음)")
    public void NotFoundRemoveWish() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/wish/9999",
            DELETE, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}