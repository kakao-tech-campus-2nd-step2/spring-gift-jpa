package gift.wishlist.controller;

import static org.assertj.core.api.Assertions.assertThat;

import gift.TestUtils;
import gift.auth.dto.LoginReqDto;
import gift.auth.service.AuthService;
import gift.auth.token.AuthToken;
import gift.auth.token.AuthTokenGenerator;
import gift.common.exception.ErrorResponse;
import gift.product.exception.ProductErrorCode;
import gift.utils.JwtTokenProvider;
import gift.wishlist.dto.WishListReqDto;
import gift.wishlist.dto.WishListResDto;
import gift.wishlist.exception.WishListErrorCode;
import java.net.URI;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("위시 리스트 컨트롤러 테스트")
class WishListControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static String baseUrl;
    private static String accessToken;

    private static Long memberId;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;

        var url = baseUrl + "/api/members/register";
        var uniqueEmail = "abc123@test.com";
        var reqBody = new LoginReqDto(uniqueEmail, "1234");
        var requestEntity = new RequestEntity<>(reqBody, HttpMethod.POST, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, AuthToken.class);

        assert actual.getBody() != null;
        accessToken = actual.getBody().accessToken();

        memberId = jwtTokenProvider.getMemberId(accessToken);

        // 위시 리스트 초기화
        // 상품은 data.sql로 10개 등록되어있음. 여기서 위시 리스트에 3개의 상품을 임의 수량으로 추가
        var wishListUrl = baseUrl + "/api/wish-list";
        List<WishListReqDto> wishListReqDtos = List.of(
                new WishListReqDto(1L, 3),
                new WishListReqDto(2L, 7),
                new WishListReqDto(3L, 5)
        );

        wishListReqDtos.forEach(wishListReqDto -> {
            var wishListRequest = TestUtils.createRequestEntity(wishListUrl, wishListReqDto, HttpMethod.POST, accessToken);
            restTemplate.exchange(wishListRequest, String.class);
        });
    }

    @AfterEach
    void tearDown() {
        // 회원 삭제
        var url = baseUrl + "/api/members/" + memberId;
        var request = TestUtils.createRequestEntity(url, null, HttpMethod.DELETE, accessToken);
        restTemplate.exchange(request, String.class);
    }

    @Test
    @DisplayName("위시 리스트 조회")
    void 위시_리스트_조회() {
        // given
        var url = baseUrl + "/api/wish-list";
        var request = TestUtils.createRequestEntity(url, null, HttpMethod.GET, accessToken);

        // when
        var responseType = new ParameterizedTypeReference<List<WishListResDto>>() {};
        var actual = restTemplate.exchange(request, responseType);

        var wishList = actual.getBody();

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(wishList).isNotNull();

        assertThat(wishList).hasSize(3);
        wishList.forEach(w -> {
            assertThat(w.id()).isNotNull();
            assertThat(w.productId()).isNotNull();
            assertThat(w.quantity()).isNotNull();
        });

        assertThat(wishList.get(0).productId()).isEqualTo(1L);
        assertThat(wishList.get(0).quantity()).isEqualTo(3);

        assertThat(wishList.get(1).productId()).isEqualTo(2L);
        assertThat(wishList.get(1).quantity()).isEqualTo(7);

        assertThat(wishList.get(2).productId()).isEqualTo(3L);
        assertThat(wishList.get(2).quantity()).isEqualTo(5);
    }

    @ParameterizedTest(name = "위시 리스트 추가 테스트 - 상품 ID: {0}, 수량: {1}")
    @MethodSource("provideWishListAddScenarios")
    @DisplayName("위시 리스트 추가")
    void 위시_리스트_추가(long productId, int quantity, int expectedTotalItems, Predicate<List<WishListResDto>> resultPredicate) {
        // given
        var url = baseUrl + "/api/wish-list";
        var reqBody = new WishListReqDto(productId, quantity);
        var request = TestUtils.createRequestEntity(url, reqBody, HttpMethod.POST, accessToken);

        // when
        var actual = restTemplate.exchange(request, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isEqualTo("상품을 장바구니에 담았습니다.");
        assertThat(actual.getHeaders().getLocation()).isEqualTo(URI.create("/api/wish-list"));

        // 위시 리스트 추가 후 조회
        var requestGet = TestUtils.createRequestEntity(url, null, HttpMethod.GET, accessToken);
        var responseType = new ParameterizedTypeReference<List<WishListResDto>>() {};
        var actualGet = restTemplate.exchange(requestGet, responseType);

        var wishList = actualGet.getBody();

        assertThat(wishList).hasSize(expectedTotalItems);
        assertThat(resultPredicate.test(wishList)).isTrue();
    }

    private static Stream<Arguments> provideWishListAddScenarios() {
        return Stream.of(
                // 새로운 상품 추가 (productId: 5L는 기존 위시 리스트에 없다고 가정)
                Arguments.of(5L, 4, 4,
                        (Predicate<List<WishListResDto>>) wishList ->
                                wishList.stream().anyMatch(w -> w.productId() == 5L && w.quantity() == 4)),

                // 기존 상품의 수량 증가 (productId: 1L는 이미 위시 리스트에 있고 수량이 3이라고 가정)
                Arguments.of(1L, 4, 3,
                        (Predicate<List<WishListResDto>>) wishList ->
                                wishList.stream().anyMatch(w -> w.productId() == 1L && w.quantity() == 7))
        );
    }

    @Test
    @DisplayName("위시 리스트 추가 실패")
    void 위시_리스트_추가_실패() {
        // given
        var url = baseUrl + "/api/wish-list";
        var reqBody = new WishListReqDto(-1L, 3);    // 존재하지 않는 상품 ID
        var request = TestUtils.createRequestEntity(url, reqBody, HttpMethod.POST, accessToken);

        // when
        var actual = restTemplate.exchange(request, ErrorResponse.class);
        ErrorResponse errorResponse = actual.getBody();

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse).isInstanceOf(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo(ProductErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
        assertThat(errorResponse.getInvalidParams()).isNull();
    }

    @ParameterizedTest(name = "위시 리스트 수정 테스트 - 수정 수량: {0}")
    @MethodSource("provideWishListUpdateScenarios")
    @DisplayName("위시 리스트 수정")
    void 위시_리스트_수정(int updatedQuantity, String expectedMessage,
            BiPredicate<List<WishListResDto>, Long> resultPredicate) {
        // given
        // 자신의 위시 리스트 조회
        var urlGet = baseUrl + "/api/wish-list";
        var requestGet = TestUtils.createRequestEntity(urlGet, null, HttpMethod.GET, accessToken);
        var responseTypeGet = new ParameterizedTypeReference<List<WishListResDto>>() {};
        var wishList = restTemplate.exchange(requestGet, responseTypeGet).getBody();

        // 수정할 마지막 요소 가져오기
        assert wishList != null;
        var lastWishList = wishList.getLast();

        // 수정할 내용 준비
        var urlUpdate = baseUrl + "/api/wish-list/" + lastWishList.id();
        var reqBodyUpdate = new WishListReqDto(lastWishList.productId(), updatedQuantity);
        var requestUpdate = TestUtils.createRequestEntity(urlUpdate, reqBodyUpdate, HttpMethod.PUT, accessToken);

        // when
        var actual = restTemplate.exchange(requestUpdate, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isEqualTo(expectedMessage);

        // 수정된 위시 리스트 조회
        var responseType = new ParameterizedTypeReference<List<WishListResDto>>() {};
        var wishListUpdated = restTemplate.exchange(requestGet, responseType).getBody();

        assertThat(resultPredicate.test(wishListUpdated, lastWishList.id())).isTrue();
    }

    private static Stream<Arguments> provideWishListUpdateScenarios() {
        return Stream.of(
                Arguments.of(7, "담긴 상품의 수량을 변경했습니다.",
                        (BiPredicate<List<WishListResDto>, Long>) (wishList, wishListId) ->
                                wishList.stream().anyMatch(w -> w.id().equals(wishListId) && w.quantity() == 7)),

                Arguments.of(3, "담긴 상품의 수량을 변경했습니다.",
                        (BiPredicate<List<WishListResDto>, Long>) (wishList, wishListId) ->
                                wishList.stream().anyMatch(w -> w.id().equals(wishListId) && w.quantity() == 3)),

                // 수량 0: 삭제
                Arguments.of(0, "담긴 상품의 수량을 변경했습니다.",
                        (BiPredicate<List<WishListResDto>, Long>) (wishList, wishListId) ->
                                wishList.stream().noneMatch(w -> w.id().equals(wishListId)))
        );
    }

    @Test
    @DisplayName("위시 리스트 삭제")
    void 위시_리스트_삭제() {
        // given
        // 자신의 위시 리스트 조회
        var urlGet = baseUrl + "/api/wish-list";
        var requestGet = TestUtils.createRequestEntity(urlGet, null, HttpMethod.GET, accessToken);
        var responseTypeGet = new ParameterizedTypeReference<List<WishListResDto>>() {};
        var wishList = restTemplate.exchange(requestGet, responseTypeGet).getBody();

        // when
        assert wishList != null;
        wishList.forEach(w -> {
            var urlDelete = baseUrl + "/api/wish-list/" + w.id();
            var requestDelete = TestUtils.createRequestEntity(urlDelete, null, HttpMethod.DELETE, accessToken);
            restTemplate.exchange(requestDelete, String.class);
        });

        // then
        // 삭제 후 위시 리스트 조회
        var actual = restTemplate.exchange(requestGet, responseTypeGet);
        var wishListAfterDelete = actual.getBody();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(wishListAfterDelete).isEmpty();
    }

    @ParameterizedTest(name = "위시 리스트 {0} 실패")
    @MethodSource("provideWishListModifyFailureScenarios")
    @DisplayName("위시 리스트 수정/삭제 실패")
    void 위시_리스트_수정_삭제_실패(String operation, HttpMethod httpMethod, Object reqBody) {
        // given
        var wishListId = -1L;   // 존재하지 않는 위시 리스트 ID
        var url = baseUrl + "/api/wish-list/" + wishListId;
        var request = TestUtils.createRequestEntity(url, reqBody, httpMethod, accessToken);

        // when
        var actual = restTemplate.exchange(request, ErrorResponse.class);
        ErrorResponse errorResponse = actual.getBody();

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse).isInstanceOf(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo(WishListErrorCode.WISH_LIST_NOT_FOUND.getCode());
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo(WishListErrorCode.WISH_LIST_NOT_FOUND.getMessage());
        assertThat(errorResponse.getInvalidParams()).isNull();
    }

    private static Stream<Arguments> provideWishListModifyFailureScenarios() {
        return Stream.of(
                Arguments.of("수정", HttpMethod.PUT, new WishListReqDto(1L, 3)),
                Arguments.of("삭제", HttpMethod.DELETE, null)
        );
    }
}