package gift.integrity;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.dto.ClientProductDto;
import gift.product.dto.MemberDto;
import gift.product.dto.ProductDto;
import gift.product.dto.WishDto;
import gift.product.service.AuthService;
import gift.product.service.ProductService;
import gift.product.service.WishService;
import java.net.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class WishesIntegrityTest {

    @LocalServerPort
    int port;
    String BASE_URL = "http://localhost:";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    AuthService authService;

    @Autowired
    ProductService productService;

    @Autowired
    WishService wishService;

    String accessToken;

    @BeforeAll
    void 로그인() {
        MemberDto memberDto = new MemberDto("test@test.com", "1234");
        authService.register(memberDto);
        accessToken = authService.login(memberDto).token();
    }

    @BeforeAll
    void 상품_추가() {
        String url = BASE_URL + port + "/api/products/insert";
        ProductDto productDto = new ClientProductDto("테스트1", 1500, "테스트주소1");
        RequestEntity<ProductDto> requestEntity = new RequestEntity<>(productDto, HttpMethod.POST,
            URI.create(url));

        testRestTemplate.exchange(requestEntity, String.class);
    }

    @Order(1)
    @Test
    void 위시리스트_추가() {
        String url = BASE_URL + port + "/api/wishes/insert";
        WishDto wishDto = new WishDto(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RequestEntity<WishDto> requestEntity = new RequestEntity<>(wishDto, headers,
            HttpMethod.POST, URI.create(url));

        var actual = testRestTemplate.exchange(requestEntity, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(2)
    @Test
    void 위시리스트_조회() {
        String url = BASE_URL + port + "/api/wishes";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RequestEntity<WishDto> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
            URI.create(url));

        var actual = testRestTemplate.exchange(requestEntity, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(3)
    @Test
    void 위시리스트_ID로_조회() {
        String url = BASE_URL + port + "/api/wishes/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RequestEntity<WishDto> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
            URI.create(url));

        var actual = testRestTemplate.exchange(requestEntity, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(4)
    @Test
    void 위시리스트_삭제() {
        String url = BASE_URL + port + "/api/wishes/delete/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RequestEntity<WishDto> requestEntity = new RequestEntity<>(headers, HttpMethod.DELETE,
            URI.create(url));

        var actual = testRestTemplate.exchange(requestEntity, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
