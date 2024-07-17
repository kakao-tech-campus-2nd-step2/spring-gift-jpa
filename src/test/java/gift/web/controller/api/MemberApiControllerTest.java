package gift.web.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.authentication.token.Token;
import gift.domain.Member;
import gift.repository.MemberRepository;
import gift.service.MemberService;
import gift.service.WishProductService;
import gift.utils.DatabaseCleanup;
import gift.utils.MemberDummyDataProvider;
import gift.utils.ProductDummyDataProvider;
import gift.utils.WishProductDummyDataProvider;
import gift.web.dto.request.LoginRequest;
import gift.web.dto.request.member.CreateMemberRequest;
import gift.web.dto.request.wishproduct.UpdateWishProductRequest;
import gift.web.dto.response.LoginResponse;
import gift.web.dto.response.member.CreateMemberResponse;
import gift.web.dto.response.member.ReadMemberResponse;
import gift.web.dto.response.wishproduct.ReadAllWishProductsResponse;
import gift.web.dto.response.wishproduct.UpdateWishProductResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberDummyDataProvider memberDummyDataProvider;

    @Autowired
    private ProductDummyDataProvider productDummyDataProvider;

    @Autowired
    private WishProductDummyDataProvider wishProductDummyDataProvider;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WishProductService wishProductService;

    //테스트용 회원
    private Member member;
    private Token token;

    @BeforeEach
    void setUp() {
        insertDummyData(100);
        member = getTestMember(1L);
        token = getAccessToken();
    }

    private Member getTestMember(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID: " + id +"인 회원이 존재하지 않습니다."));
    }

    private Token getAccessToken() {
        LoginRequest loginRequest = new LoginRequest(
            member.getEmail().getValue(),
            member.getPassword().getValue()
        );
        LoginResponse loginResponse = memberService.login(loginRequest);
        return loginResponse.getToken();
    }

    private void insertDummyData(int quantity) {
        if (quantity < 2) {
            throw new IllegalArgumentException("quantity는 2 이상이어야 합니다.");
        }
        memberDummyDataProvider.run(quantity);
        productDummyDataProvider.run(quantity);
        wishProductDummyDataProvider.run(quantity);
    }

    @AfterEach
    void tearDown() {
        databaseCleanup.execute();
    }

    @Test
    @DisplayName("회원 생성 요청에 대한 정상 응답")
    void createMember() {
        //given
        CreateMemberRequest request = new CreateMemberRequest("test@gmail.com", "test1234", "test");
        String url = "http://localhost:" + port + "/api/members/register";

        //when
        ResponseEntity<CreateMemberResponse> response = restTemplate.postForEntity(url, request, CreateMemberResponse.class);

        //then
        Long newMemberId = response.getBody().getId();
        ReadMemberResponse findMember = memberService.readMember(newMemberId);

        assertAll(
            () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
            () -> assertThat(newMemberId).isEqualTo(findMember.getId()),
            () -> assertThat(request.getEmail()).isEqualTo(findMember.getEmail()),
            () -> assertThat(request.getName()).isEqualTo(findMember.getName()),
            () -> assertThat(request.getPassword()).isEqualTo(findMember.getPassword())
        );
    }

    @Test
    @DisplayName("로그인 요청에 대한 정상 응답")
    void login() {
        //given
        String url = "http://localhost:" + port + "/api/members/login";
        String email = member.getEmail().getValue();
        String password = member.getPassword().getValue();
        LoginRequest request = new LoginRequest(email, password);

        //when
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(url, request, LoginResponse.class);

        //then
        assertAll(
            () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
            () -> assertThat(response.getBody().getToken()).isNotNull()
        );
    }

    @Test
    @DisplayName("위시 리스트 조회 요청에 대한 정상 응답")
    void readWishProduct() {
        //given
        String url = "http://localhost:" + port + "/api/members/wishlist";
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        PageRequest defaultPageRequest = PageRequest.of(0, 10);
        ReadAllWishProductsResponse expectedWishProducts = wishProductService.readAllWishProducts(member.getId(),
            defaultPageRequest);

        //when
        ResponseEntity<ReadAllWishProductsResponse> response = restTemplate.exchange(url,
            HttpMethod.GET, httpEntity, ReadAllWishProductsResponse.class);

        //then
        assertAll(
            () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
            () -> assertIterableEquals(response.getBody().getWishlist(), expectedWishProducts.getWishlist())
        );
    }

    @Test
    @DisplayName("위시 리스트 상품 수정 요청에 대한 정상 응답")
    void updateWishProduct() {
        //given
        Long wishProductId = 1L;
        String url = "http://localhost:" + port + "/api/members/wishlist/" + wishProductId;
        HttpHeaders httpHeaders = getHttpHeaders();

        UpdateWishProductRequest request = new UpdateWishProductRequest(3);

        HttpEntity httpEntity = new HttpEntity(request, httpHeaders);

        //when
        ResponseEntity<UpdateWishProductResponse> response = restTemplate.exchange(url,
            HttpMethod.PUT, httpEntity, UpdateWishProductResponse.class);

        //then
        assertAll(
            () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
            () -> assertThat(response.getBody().getQuantity()).isEqualTo(request.getQuantity())
        );
    }

    @Test
    @DisplayName("위시 리스트 상품 삭제 요청에 대한 정상 응답")
    void deleteWishProduct() {
        //given
        Long wishProductId = 2L;
        String url = "http://localhost:" + port + "/api/members/wishlist/" + wishProductId;
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        //when
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity,
            Void.class);

        //then
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.getValue());
        return httpHeaders;
    }
}