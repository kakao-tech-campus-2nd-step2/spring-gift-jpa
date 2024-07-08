package gift;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.product.ProductDTO;
import gift.domain.product.Product;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private Map<Long, Product> products;

    @BeforeEach
    void setUp() {
        products = new HashMap<>();
    }

    /**
     * 싱품 추가 테스트
     */
    @Test
    void postProductTest() {
        //given
        String name = "아이스 아메리카노 T";
        int price = 4500;
        String imageUrl = "testImageUrl.com";

        ProductDTO productDTO = new ProductDTO(name, price, imageUrl);
        String url = "http://localhost:" + port + "/api/products";

        //when
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, productDTO,
            Object.class);

        //then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Product product = objectMapper.convertValue(responseEntity.getBody(), Product.class);

        assertThat(product.getName()).isEqualTo("아이스 아메리카노 T");
        assertThat(product.getPrice()).isEqualTo(4500);
        assertThat(product.getImageUrl()).isEqualTo("testImageUrl.com");
    }

    /**
     * 상품 조회 테스트
     */
    @Test
    void getProductTest() {
        //given
        String name = "아이스 아메리카노 T";
        int price = 4500;
        String imageUrl = "testImageUrl.com";

        ProductDTO productDTO = new ProductDTO(name, price, imageUrl);
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<Object> postResponseEntity = restTemplate.postForEntity(url, productDTO,
            Object.class);
        Product postProduct = objectMapper.convertValue(postResponseEntity.getBody(),
            Product.class);
        Long postId = postProduct.getId();
        System.out.println("postProduct = " + postProduct);

        //when
        url = "http://localhost:" + port + "/api/products/" + postId;
        ResponseEntity<Object> getResponseEntity = restTemplate.getForEntity(url, Object.class);
        Product getProduct = objectMapper.convertValue(getResponseEntity.getBody(), Product.class);
        Long getId = getProduct.getId();
        System.out.println("getProduct = " + getProduct);

        //then
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getProduct.getId()).isEqualTo(postId);
        assertThat(getProduct.getName()).isEqualTo(name);
        assertThat(getProduct.getPrice()).isEqualTo(price);
        assertThat(getProduct.getImageUrl()).isEqualTo(imageUrl);
    }

    /**
     * 상품 수정 테스트
     */
    @Test
    void updateProductTest() {
        //given
        String name = "아이스 아메리카노 T";
        int price = 4500;
        String imageUrl = "testImageUrl.com";

        ProductDTO productDTO = new ProductDTO(name, price, imageUrl);
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<Object> postResponseEntity = restTemplate.postForEntity(url, productDTO,
            Object.class);
        Product postProduct = objectMapper.convertValue(postResponseEntity.getBody(),
            Product.class);
        Long postId = postProduct.getId();

        //when
        ProductDTO updateProductDTO = new ProductDTO(postProduct.getName(), 4700,
            postProduct.getImageUrl());
        String updateUrl = "http://localhost:" + port + "/api/products/" + postId;
        restTemplate.put(updateUrl, updateProductDTO, Object.class);

        String getUrl = updateUrl;
        ResponseEntity<Object> getResponseEntity = restTemplate.getForEntity(getUrl, Object.class);
        Product getProduct = objectMapper.convertValue(getResponseEntity.getBody(), Product.class);

        //then
        assertThat(getProduct.getId()).isEqualTo(postId);
        assertThat(getProduct.getName()).isEqualTo(name);
        assertThat(getProduct.getPrice()).isEqualTo(4700);
        assertThat(getProduct.getImageUrl()).isEqualTo(imageUrl);
    }

    /**
     * 상품 삭제 테스트
     */
    @Test
    void deleteProduct() {
        //given
        String name = "아이스 아메리카노 T";
        int price = 4500;
        String imageUrl = "testImageUrl.com";

        ProductDTO productDTO = new ProductDTO(name, price, imageUrl);
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<Object> postResponseEntity = restTemplate.postForEntity(url, productDTO,
            Object.class);
        Product product = objectMapper.convertValue(postResponseEntity.getBody(), Product.class);
        Long id = product.getId();

        //when
        String deleteUrl = "http://localhost:" + port + "/api/products/" + id;
        restTemplate.delete(deleteUrl);

        //then
        String getUrl = "http://localhost:" + port + "/api/products/" + id;
        ResponseEntity<String> getResponseEntity = restTemplate.getForEntity(getUrl, String.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponseEntity.getBody()).isEqualTo("해당 ID의 상품이 존재하지 않습니다.");
    }
}
