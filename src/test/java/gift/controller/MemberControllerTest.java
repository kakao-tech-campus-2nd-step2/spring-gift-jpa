package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.dto.MemberRequestDto;
import java.net.URI;
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
class MemberControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void register() {
        var url = "http://localhost:" + port + "/api/members/register";
        var request = new MemberRequestDto("member2@example.com", "password2", "member2", "user");
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void login() {
        var url = "http://localhost:" + port + "/api/members/register";
        var request = new MemberRequestDto("member1@example.com", "password", "member1", "user");
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));
        restTemplate.exchange(requestEntity, String.class);

        url = "http://localhost:" + port + "/api/members/login";
        requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
