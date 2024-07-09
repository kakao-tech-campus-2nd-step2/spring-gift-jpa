package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import gift.auth.Login;
import gift.domain.User.CreateUser;
import gift.domain.User.UpdateUser;
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
class UserTest {

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
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/login",
            POST,
            requestEntity, String.class);

        int startIndex = responseEntity.getBody().indexOf("\"token\":\"") + "\"token\":\"".length();

        int endIndex = responseEntity.getBody().indexOf("\"", startIndex);

        token = responseEntity.getBody().substring(startIndex, endIndex);

    }

    @Test
    @DisplayName("유저 수정")
    public void UpdateUser() {
        UpdateUser body = new UpdateUser("123456789");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/user/1",
            PUT, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("유저 수정(유저 없음)")
    public void NotFoundUpdateUser() {
        UpdateUser body = new UpdateUser("123456789");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/user/0",
            PUT, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("유저 생성")
    public void CreateUser() {
        CreateUser body = new CreateUser("kakao10@kakao.com", "1234");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/user",
            POST, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

//    병렬로 수행하면 오류나지만, 단독으로 수행하면 수행 됨
    @Test
    @DisplayName("유저 생성(유저 중복)")
    public void DuplicateCreateUser() {
        CreateUser body = new CreateUser("kakao1@kakao.com", "1234");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/user",
            POST,
            requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @Test
    @DisplayName("유저 삭제")
    public void removeUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/user/2",
            DELETE, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("유저 삭제(유저 없음)")
    public void NotFoundRemoveUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + port + "/api/user/9999",
            DELETE, requestEntity, String.class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

}