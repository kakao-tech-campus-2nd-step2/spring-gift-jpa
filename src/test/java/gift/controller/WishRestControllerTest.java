//package gift.controller;
//
//import gift.model.CreateWishRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
//
//import java.net.URI;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WishRestControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    void create() {
//        // given
//        // 회원 가입을 해서 토큰을 발급 받는다.
//        var token = "";
//
//        // 상품을 등록한다
//        var productId = 1L;
//
//        var url = "";
//        var request = new CreateWishRequest("test@naver.com", productId, 3);
//        var headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        var requestEntity = new RequestEntity<>(request, headers, HttpMethod.POST, URI.create(url));
//
//        // when
//        var actual = restTemplate.exchange(requestEntity, String.class);
//
//        // then
//        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(actual.getHeaders().containsKey(HttpHeaders.LOCATION)).isTrue();
//    }
//}
