package gift.controller;

import gift.model.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void test() {
        var url = "http://localhost:" + port;
        assertThat(port).isNotZero();
    }

    @Test
    void create() {
        var url = "http://localhost:" + port + "/api/products";
        CreateProductRequest request = new CreateProductRequest("product", 1_000, "image.jpg");
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(actual.getHeaders().containsKey(HttpHeaders.LOCATION)).isTrue();
    }
}
