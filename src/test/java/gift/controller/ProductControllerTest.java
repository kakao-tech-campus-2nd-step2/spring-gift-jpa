package gift.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.dto.product.AddProductRequest;
import gift.dto.product.UpdateProductRequest;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Port() {
        assertThat(port).isNotZero();
    }

    @Test
    @DisplayName("create test")
    void createTest() {
        // given
        var url = "http://localhost:" + port + "/api/products";
        var request = new AddProductRequest( "product", 1_000, "image.jpg");
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));

        // when
        var actual = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getHeaders().containsKey(HttpHeaders.LOCATION)).isTrue();
        assertThat(actual.getBody().contains("id")).isNotNull();
    }

    @Test
    @DisplayName("update test")
    void updateTest() {
        // given
        var url = "http://localhost:" + port + "/api/products/" + 1L;
        var request = new UpdateProductRequest("product", 1_000, "image.jpg");
        var requestEntity = new RequestEntity<>(request, HttpMethod.PUT, URI.create(url));

        // when
        var actual = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getHeaders().containsKey(HttpHeaders.LOCATION)).isTrue();
        assertThat(actual.getBody().contains("id")).isTrue();
    }

    @Test
    @DisplayName("delete test")
    void deleteTest() {
        // given
        var url = "http://localhost:" + port + "/api/products/" + 1L;
        var requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(url));
        var getUrl = "http://localhost:" + port + "/api/products/" + 1L;
        var getRequestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(getUrl));

        // when
        var actual = restTemplate.exchange(requestEntity, String.class);
        var getResponse = restTemplate.exchange(getRequestEntity, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
