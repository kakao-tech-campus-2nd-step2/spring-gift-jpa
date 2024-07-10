//package gift.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import gift.classes.RequestState.RequestStateDTO;
//import gift.classes.RequestState.RequestStatus;
//import gift.classes.RequestState.SecureRequestStateDTO;
//import gift.classes.RequestState.WishlistRequestStateDTO;
//import gift.dto.MemberDto;
//import gift.dto.ProductDto;
//import gift.dto.RequestWishDto;
//import gift.services.ProductService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
//
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@Transactional
//class WishControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private ProductService productService;
//
//    private String token;
//
//    @BeforeEach
//    @Sql(scripts = "/reset-database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
//    void setUp() {
//        // 회원 등록
//        var registerUrl = "http://localhost:" + port + "/api/member/register";
//        MemberDto memberDto = new MemberDto(1L, "testemail", "password", "admin");
//
//        HttpEntity<MemberDto> registerRequest = new HttpEntity<>(memberDto);
//        ResponseEntity<RequestStateDTO> registerResponse = restTemplate.exchange(registerUrl, HttpMethod.POST, registerRequest, RequestStateDTO.class);
//        assertEquals(200, registerResponse.getStatusCodeValue());
//
//        // 로그인
//        var loginUrl = "http://localhost:" + port + "/api/member/login";
//        HttpEntity<MemberDto> loginRequest = new HttpEntity<>(memberDto);
//        ResponseEntity<SecureRequestStateDTO> loginResponse = restTemplate.exchange(loginUrl, HttpMethod.POST, loginRequest, SecureRequestStateDTO.class);
//        assertEquals(200, loginResponse.getStatusCodeValue());
//        token = loginResponse.getBody().getSecure();
//
//        // 제품 추가
//        ProductDto productDto = new ProductDto(null, "Product1", 100.0, "imageUrl");
//        productService.addProduct(productDto);
//    }
//
//    @Test
//    @Order(1)
//    void addWishTest() {
//
//        var url = "http://localhost:" + port + "/api/wishlist";
//        RequestWishDto requestWishDto = new RequestWishDto(1L);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("authorization", "Bearer " + token);
//
//        HttpEntity<RequestWishDto> request = new HttpEntity<>(requestWishDto, headers);
//
//        ResponseEntity<RequestStateDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, RequestStateDTO.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(RequestStatus.success, response.getBody().getStatus());
//    }
//
//    @Test
//    @Order(2)
//    void getWishlistByIdTest() {
//        var url = "http://localhost:" + port + "/api/wishlist";
//
////        add Wish
//        RequestWishDto requestWishDto = new RequestWishDto(1L);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("authorization", "Bearer " + token);
//        HttpEntity<RequestWishDto> addrequest = new HttpEntity<>(requestWishDto, headers);
//        ResponseEntity<RequestStateDTO> addresponse = restTemplate.exchange(url, HttpMethod.POST, addrequest, RequestStateDTO.class);
//        assertEquals(200, addresponse.getStatusCodeValue());
//
////        Id로 Wishlist 가져오기
//        HttpEntity<Void> request = new HttpEntity<>(headers);
//        ResponseEntity<WishlistRequestStateDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, WishlistRequestStateDTO.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertFalse(response.getBody().getWishes().isEmpty());
//    }
//
//    @Test
//    @Order(3)
//    void deleteWishTest() {
//        var url = "http://localhost:" + port + "/api/wishlist";
//
//        //        add Wish
//        RequestWishDto addrequestWishDto = new RequestWishDto(1L);
//        HttpHeaders addheaders = new HttpHeaders();
//        addheaders.set("authorization", "Bearer " + token);
//        HttpEntity<RequestWishDto> addrequest = new HttpEntity<>(addrequestWishDto, addheaders);
//        ResponseEntity<RequestStateDTO> addresponse = restTemplate.exchange(url, HttpMethod.POST, addrequest, RequestStateDTO.class);
//        assertEquals(200, addresponse.getStatusCodeValue());
//
//        // 위시 삭제
//        RequestWishDto requestWishDto = new RequestWishDto(1L);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("authorization", "Bearer " + token);
//        HttpEntity<RequestWishDto> request = new HttpEntity<>(requestWishDto, headers);
//        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//}