package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EndToEndTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void addProduct() throws Exception {
        //given
        ProductReqDto reqDto = new ProductReqDto("초콜릿", 5000, "https://cdn.aws.com/chocolate.jpg");

        //when
        ProductResDto result = restClient.post()
                .uri("/api/products")
                .body(reqDto)
                .retrieve()
                .body(ProductResDto.class);

        //then
        assert result != null;
        assertThat(result.name()).isEqualTo("초콜릿");
        assertThat(result.price()).isEqualTo(5000);
        assertThat(result.imageUrl()).isEqualTo("https://cdn.aws.com/chocolate.jpg");
        assertThat(result.id()).isNotNull();
    }

    @Test
    @DisplayName("상품을 조회한다.")
    void getProduct() throws Exception {
        //given
        ProductReqDto reqDto = new ProductReqDto("초콜릿", 5000, "https://cdn.aws.com/chocolate.jpg");
        ProductResDto addedProduct = restClient.post()
                .uri("/api/products")
                .body(reqDto)
                .retrieve()
                .body(ProductResDto.class);

        //when
        assert addedProduct != null;
        ProductResDto result = restClient.get()
                .uri("/api/products/" + addedProduct.id())
                .retrieve()
                .body(ProductResDto.class);

        //then
        assert result != null;
        assertThat(result.name()).isEqualTo("초콜릿");
        assertThat(result.price()).isEqualTo(5000);
        assertThat(result.imageUrl()).isEqualTo("https://cdn.aws.com/chocolate.jpg");
        assertThat(result.id()).isEqualTo(addedProduct.id());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void updateProduct() throws Exception {
        //given
        ProductReqDto reqDto = new ProductReqDto("초콜릿", 5000, "https://cdn.aws.com/chocolate.jpg");
        ProductResDto addedProduct = restClient.post()
                .uri("/api/products")
                .body(reqDto)
                .retrieve()
                .body(ProductResDto.class);

        //when
        assert addedProduct != null;
        ProductReqDto updateReqDto = new ProductReqDto("화이트초콜릿", 6000, "https://cdn.aws.com/chocolate.jpg");
        Boolean result = restClient.put()
                .uri("/api/products/" + addedProduct.id())
                .body(updateReqDto)
                .retrieve()
                .body(Boolean.class);

        // 수정된 상품 조회
        ProductResDto updatedProduct = restClient.get()
                .uri("/api/products/" + addedProduct.id())
                .retrieve()
                .body(ProductResDto.class);

        //then
        assert result != null;
        assertThat(result).isTrue();

        assert updatedProduct != null;
        assertThat(updatedProduct.name()).isEqualTo("화이트초콜릿");
        assertThat(updatedProduct.price()).isEqualTo(6000);
        assertThat(updatedProduct.imageUrl()).isEqualTo("https://cdn.aws.com/chocolate.jpg");
        assertThat(updatedProduct.id()).isEqualTo(addedProduct.id());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProduct() throws Exception {
        //given
        ProductReqDto reqDto = new ProductReqDto("초콜릿", 5000, "https://cdn.aws.com/chocolate.jpg");
        ProductResDto addedProduct = restClient.post()
                .uri("/api/products")
                .body(reqDto)
                .retrieve()
                .body(ProductResDto.class);

        //when
        assert addedProduct != null;
        Boolean result = restClient.delete()
                .uri("/api/products/" + addedProduct.id())
                .retrieve()
                .body(Boolean.class);

        //then
        assert result != null;
        assertThat(result).isTrue();

        // 삭제된 상품을 조회하면 에러가 발생해야 한다.
        Assertions.assertThrows(RestClientException.class, () -> {
            restClient.get()
                    .uri("/api/products/" + addedProduct.id())
                    .retrieve()
                    .body(ProductResDto.class);
        });
    }
}
