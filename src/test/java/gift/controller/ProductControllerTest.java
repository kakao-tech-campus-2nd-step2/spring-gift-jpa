package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.model.dto.MemberRequestDto;
import gift.model.dto.ProductRequestDto;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws JsonProcessingException {
        // token 가져오기
        var tokenUrl = "http://localhost:" + port + "/api/members/register";
        var tokenRequest = new MemberRequestDto("member1@example.com", "password", "member1",
            "user");
        var tokenRequestEntity = new RequestEntity<>(tokenRequest, HttpMethod.POST,
            URI.create(tokenUrl));
        var tokenResponseEntity = restTemplate.exchange(tokenRequestEntity, String.class);
        var token = objectMapper.readTree(tokenResponseEntity.getBody()).get("accessToken")
            .asText();
        System.out.println(token);
        //products add
        var url = "http://localhost:" + port + "/api/products";
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        var expected1 = new ProductRequestDto("gamza", 500, "gamza.jpg");
        var expected1RequestEntity = new RequestEntity<>(expected1, headers, HttpMethod.POST,
            URI.create(url));
        restTemplate.exchange(expected1RequestEntity, String.class);
        var expected2 = new ProductRequestDto("goguma", 1000, "goguma.jpg");
        var expected2RequestEntity = new RequestEntity<>(expected2, headers, HttpMethod.POST,
            URI.create(url));
        restTemplate.exchange(expected2RequestEntity, String.class);

        var requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getBody()).isEqualTo(
            "[{\"id\":1,\"name\":\"gamza\",\"price\":500,\"imageUrl\":\"gamza.jpg\"},{\"id\":2,\"name\":\"goguma\",\"price\":1000,\"imageUrl\":\"goguma.jpg\"}]");
    }
}
