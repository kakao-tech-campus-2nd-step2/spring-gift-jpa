package gift.web.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.authentication.token.Token;
import gift.domain.Member;
import gift.domain.vo.Email;
import gift.domain.vo.Password;
import gift.service.MemberService;
import gift.service.WishProductService;
import gift.web.dto.request.LoginRequest;
import gift.web.dto.request.member.CreateMemberRequest;
import gift.web.dto.request.wishproduct.UpdateWishProductRequest;
import gift.web.dto.response.LoginResponse;
import gift.web.dto.response.member.CreateMemberResponse;
import gift.web.dto.response.member.ReadMemberResponse;
import gift.web.dto.response.wishproduct.ReadAllWishProductsResponse;
import gift.web.dto.response.wishproduct.UpdateWishProductResponse;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WishProductService wishProductService;

    //테스트용 회원 정보
    private Member member = new Member(1L, Email.from("member01@gmail.com"), Password.from("member010101"), "member01");
    private Token token;

    @BeforeEach
    void setUp() {
        String email = member.getEmail().getValue();
        String password = member.getPassword().getValue();
        LoginRequest loginRequest = new LoginRequest(email, password);

        LoginResponse loginResponse = memberService.login(loginRequest);

        token = loginResponse.getToken();
    }

    @Test
    void createMember() {
        //given
        CreateMemberRequest request = new CreateMemberRequest("test@gmail.com", "test1234", "test");
        String url = "http://localhost:" + port + "/api/members/register";

        //when
        ResponseEntity<CreateMemberResponse> response = restTemplate.postForEntity(url, request, CreateMemberResponse.class);

        //then
        Long newMemberId = response.getBody().getId();
        ReadMemberResponse findMember = memberService.readMember(newMemberId);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(newMemberId).isEqualTo(findMember.getId());
        assertThat(request.getEmail()).isEqualTo(findMember.getEmail());
        assertThat(request.getName()).isEqualTo(findMember.getName());
        assertThat(request.getPassword()).isEqualTo(findMember.getPassword());
    }

    @Test
    void login() {
        //given
        String url = "http://localhost:" + port + "/api/members/login";
        String email = member.getEmail().getValue();
        String password = member.getPassword().getValue();
        LoginRequest request = new LoginRequest(email, password);

        //when
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(url, request, LoginResponse.class);

        //then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody().getToken()).isNotNull();
    }

    @Test
    void readWishProduct() {
        //given
        String url = "http://localhost:" + port + "/api/members/wishlist";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.getValue());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ReadAllWishProductsResponse expectedWishProducts = wishProductService.readAllWishProducts(member.getId());

        //when
        ResponseEntity<ReadAllWishProductsResponse> response = restTemplate.exchange(url,
            HttpMethod.GET, httpEntity, ReadAllWishProductsResponse.class);

        //then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertIterableEquals(response.getBody().getWishlist(), expectedWishProducts.getWishlist());
    }

    @Test
    void updateWishProduct() {
        //given
        Long wishProductId = 1L;
        String url = "http://localhost:" + port + "/api/members/wishlist/" + wishProductId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.getValue());

        UpdateWishProductRequest request = new UpdateWishProductRequest(wishProductId, 3);

        HttpEntity httpEntity = new HttpEntity(request, httpHeaders);

        //when
        ResponseEntity<UpdateWishProductResponse> response = restTemplate.exchange(url,
            HttpMethod.PUT, httpEntity, UpdateWishProductResponse.class);

        //then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody().getQuantity()).isEqualTo(request.getQuantity());
    }

    @Test
    void deleteWishProduct() {
    }
}