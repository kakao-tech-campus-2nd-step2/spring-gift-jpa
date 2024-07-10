package gift.controller;

import static org.junit.jupiter.api.Assertions.*;

import gift.classes.RequestState.RequestStateDTO;
import gift.classes.RequestState.SecureRequestStateDTO;
import gift.dto.MemberDto;
import gift.dto.ProductDto;
import gift.dto.RequestWishDto;
import gift.dto.WishDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WishControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String token;

    @BeforeEach
    void setUp() {
        // 회원 등록
        var registerUrl = "http://localhost:" + port + "/api/member/register";
        MemberDto memberDto = new MemberDto(1L, "testemail", "password", "admin");

        HttpEntity<MemberDto> registerRequest = new HttpEntity<>(memberDto);
        ResponseEntity<RequestStateDTO> registerResponse = restTemplate.exchange(registerUrl, HttpMethod.POST, registerRequest, RequestStateDTO.class);
        assertEquals(200, registerResponse.getStatusCodeValue());

        // 로그인
        var loginUrl = "http://localhost:" + port + "/api/member/login";
        HttpEntity<MemberDto> loginRequest = new HttpEntity<>(memberDto);
        ResponseEntity<SecureRequestStateDTO> loginResponse = restTemplate.exchange(loginUrl, HttpMethod.POST, loginRequest, SecureRequestStateDTO.class);
        assertEquals(200, loginResponse.getStatusCodeValue());
        token = loginResponse.getBody().getSecure();

        // 제품 추가
        jdbcTemplate.execute("DROP TABLE IF EXISTS products");
        jdbcTemplate.execute("CREATE TABLE products (id LONG AUTO_INCREMENT, name VARCHAR(255), price NUMERIC, imageUrl VARCHAR(255))");
        jdbcTemplate.execute("INSERT INTO products (name, price, imageUrl) VALUES ('Product1', 100.0, 'imageUrl')");

    }

    @Test
    void addWishTest() {

        var url = "http://localhost:" + port + "/api/wishlist";
        RequestWishDto requestWishDto = new RequestWishDto(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);

        HttpEntity<RequestWishDto> request = new HttpEntity<>(requestWishDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("done", response.getBody());
    }

    @Test
    void getWishlistByIdTest() {
        var url = "http://localhost:" + port + "/api/wishlist";

//        add Wish
        RequestWishDto requestWishDto = new RequestWishDto(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);
        HttpEntity<RequestWishDto> addrequest = new HttpEntity<>(requestWishDto, headers);
        ResponseEntity<String> addresponse = restTemplate.exchange(url, HttpMethod.POST, addrequest, String.class);
        assertEquals(200, addresponse.getStatusCodeValue());

//        Id로 Wishlist 가져오기
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<WishDto[]> response = restTemplate.exchange(url, HttpMethod.GET, request, WishDto[].class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteWishTest() {
        var url = "http://localhost:" + port + "/api/wishlist";

        //        add Wish
        RequestWishDto requestAddWishDto = new RequestWishDto(1L);
        HttpHeaders addheaders = new HttpHeaders();
        addheaders.set("authorization", "Bearer " + token);
        HttpEntity<RequestWishDto> addRequest = new HttpEntity<>(requestAddWishDto, addheaders);
        ResponseEntity<String> addResponse = restTemplate.exchange(url, HttpMethod.POST, addRequest, String.class);
        assertEquals(200, addResponse.getStatusCodeValue());

        // 위시 삭제
        RequestWishDto requestWishDto = new RequestWishDto(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);
        HttpEntity<RequestWishDto> request = new HttpEntity<>(requestWishDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

}