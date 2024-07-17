package gift.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.controller.apiResponse.ProductAddApiResponse;
import gift.domain.controller.apiResponse.ProductListApiResponse;
import gift.domain.dto.request.ProductRequest;
import gift.domain.dto.response.ProductResponse;
import gift.global.apiResponse.BasicApiResponse;
import gift.utilForTest.TestUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class ProductDomainTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestUtil testUtil;

    private List<ProductResponse> getProductListByRequest() {
        return Objects.requireNonNull(restTemplate.exchange(
            new RequestEntity<>(null, new HttpHeaders(), HttpMethod.GET, testUtil.getUri(port, "/api/products")),
            ProductListApiResponse.class).getBody()).getProducts();
    }

    @Test
    @DisplayName("API test: get Product list")
    void getProducts() {
        //given
        List<ProductRequest> request = new ArrayList<>(List.of(
            new ProductRequest("product1", 1_000, "image1.jpg"),
            new ProductRequest("product2", 2_000, "image2.jpg"),
            new ProductRequest("product3", 3_000, "image3.jpg")));
        request.sort(Comparator.comparing(ProductRequest::name));
        for (var req: request) {
            restTemplate.exchange(
                new RequestEntity<>(req, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
                ProductAddApiResponse.class);
        }

        //when
        var actualResponse =
            restTemplate.exchange(
                new RequestEntity<>(null, new HttpHeaders(), HttpMethod.GET, testUtil.getUri(port, "/api/products")),
                ProductListApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        List<ProductResponse> response = getProductListByRequest();
        for (var req: request) {
            ProductResponse resp = response.stream().filter(r -> r.name().equals(req.name())).findAny().get();
            assertThat(resp.name()).isEqualTo(req.name());
            assertThat(resp.price()).isEqualTo(req.price());
            assertThat(resp.imageUrl()).isEqualTo(req.imageUrl());
        }
    }

    @Test
    @DisplayName("API test: add Product")
    void addProduct() {
        //given
        ProductRequest request = new ProductRequest("product", 1_000, "image.jpg");

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(request, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
            ProductAddApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode())
            .isEqualTo(HttpStatus.CREATED);
        assertThat(actualResponse.getHeaders().containsKey(HttpHeaders.LOCATION))
            .isTrue();
        ProductResponse createdProduct = Objects.requireNonNull(actualResponse.getBody()).getCreatedProduct();
        assertThat(createdProduct.name()).isEqualTo(request.name());
        assertThat(createdProduct.price()).isEqualTo(request.price());
        assertThat(createdProduct.imageUrl()).isEqualTo(request.imageUrl());
    }

    @Test
    @DisplayName("API test: update Product")
    void updateProduct() {
        //given
        ProductRequest request = new ProductRequest("product", 1_000, "image.jpg");
        Long createdId = Objects.requireNonNull(restTemplate.exchange(
            new RequestEntity<>(request, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
            ProductAddApiResponse.class).getBody()).getCreatedProduct().id();
        ProductRequest toUpdateRequest = new ProductRequest("newProductName", 5_000, "newImage.jpg");

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(toUpdateRequest, HttpMethod.PUT, testUtil.getUri(port, "/api/products/{id}", createdId)),
            BasicApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Product List를 요청한 뒤 given에서 생성된 product를 추출
        var updatedProduct = getProductListByRequest().stream()
                .filter(p -> p.id().equals(createdId)).findAny().get();

        assertThat(updatedProduct.name()).isEqualTo(toUpdateRequest.name());
        assertThat(updatedProduct.price()).isEqualTo(toUpdateRequest.price());
        assertThat(updatedProduct.imageUrl()).isEqualTo(toUpdateRequest.imageUrl());
    }

    @Test
    @DisplayName("API test: delete Product")
    void deleteProduct() {
        //given
        ProductRequest request = new ProductRequest("product", 1_000, "image.jpg");
        Long createdId = Objects.requireNonNull(restTemplate.exchange(
            new RequestEntity<>(request, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
            ProductAddApiResponse.class).getBody()).getCreatedProduct().id();

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(null, new HttpHeaders(), HttpMethod.DELETE, testUtil.getUri(port, "/api/products/{id}", createdId)),
            BasicApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getProductListByRequest().stream().filter(p -> p.id().equals(createdId)).findAny())
            .isEqualTo(Optional.empty());
    }
}