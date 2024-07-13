package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.member.dto.MemberRequest;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegisterTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("회원가입 E2E 테스트")
    void register() {
        var request = new MemberRequest("test@google.co.kr", "password");
        var url = "http://localhost:" + port + "/api/members/register";
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
