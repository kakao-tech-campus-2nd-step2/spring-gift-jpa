package gift;
import gift.model.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;


import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void testAddGiftToCart() {
        // 회원가입
        String registerUrl = baseUrl + "/auth/register";
        UserRequest registerRequest = new UserRequest("testuser@example.com", "password");

        HttpEntity<UserRequest> registerRequestEntity = new HttpEntity<>(registerRequest);
        ResponseEntity<String> registerResponse = restTemplate.postForEntity(registerUrl, registerRequestEntity, String.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(registerResponse.getBody()).isEqualTo("회원가입이 정상적으로 완료되었습니다.");

        // 로그인
        String loginUrl = baseUrl + "/auth/login";
        UserRequest loginRequest = new UserRequest("testuser@example.com", "password");

        HttpEntity<UserRequest> loginRequestEntity = new HttpEntity<>(loginRequest);
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequestEntity, Map.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).containsKeys("accessToken");

        String token = (String) loginResponse.getBody().get("accessToken");

        // 위시리스트에 상품 추가
        String giftId = "1";
        String addGiftUrl = baseUrl + "/wish/" + giftId + "?quantity=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> addGiftRequestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> addGiftResponse = restTemplate.exchange(
                addGiftUrl,
                HttpMethod.POST,
                addGiftRequestEntity,
                String.class
        );

        assertThat(addGiftResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addGiftResponse.getBody()).contains("위시리스트에 상품이 추가되었습니다.");
    }
}