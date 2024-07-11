package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.ProductRequest;
import gift.entity.ProductEntity;
import gift.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void readAll() {
        var url = "http://localhost:" + port + "/api/products";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        var actual = restTemplate.exchange(request, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    void read() {
        ProductEntity product = productRepository.save(new ProductEntity("test", 1000,"test.jpg"));
        System.out.println("테스트" + product.getName() +"id 값"+ product.getId());

        var url = "http://localhost:" + port + "/api/products/1";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        var actual = restTemplate.exchange(request, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Product 생성 API 테스트")
    @Transactional
    void create() {
        var request = new ProductRequest("product", 1000, "image.jpg");
        var url = "http://localhost:" + port + "/api/products";
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Transactional
    void update() {
        ProductEntity product = productRepository.save(new ProductEntity("test", 1000,"test.jpg"));
        var id = 1L;
        var request = new ProductRequest("product", 1000, "image.jpg");
        var url = "http://localhost:" + port + "/api/products/" + id;
        var requestEntity = new RequestEntity<>(request, HttpMethod.PUT, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    void delete() {
        ProductEntity product = productRepository.save(new ProductEntity("test", 1000,"test.jpg"));
        var id = 1L;
        var url = "http://localhost:" + port + "/api/products/" + id;

        var requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}