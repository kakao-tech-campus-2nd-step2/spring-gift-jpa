package gift.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void port() {
        assertThat(port).isNotZero();
    }

    @Test
    void create() {
        var url = "http://localhost:" + port + "/products";
        var request = new CreateProductRequest("product", 1_000, "image.jpg");
        var requestEntity = new RequestEntity<>(request, POST, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(CREATED);
        assertThat(actual.getHeaders().containsKey(LOCATION)).isTrue();
    }
}