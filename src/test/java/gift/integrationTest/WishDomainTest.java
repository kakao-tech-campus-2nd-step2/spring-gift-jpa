package gift.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.controller.apiResponse.WishAddApiResponse;
import gift.domain.controller.apiResponse.WishListApiResponse;
import gift.domain.controller.apiResponse.WishUpdateApiResponse;
import gift.domain.dto.request.WishDeleteRequest;
import gift.domain.dto.request.WishRequest;
import gift.domain.dto.response.WishAddResponse;
import gift.domain.dto.response.WishResponse;
import gift.domain.entity.Wish;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.exception.ProductNotIncludedInWishlistException;
import gift.domain.repository.WishRepository;
import gift.global.apiResponse.ErrorApiResponse;
import gift.utilForTest.TestUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
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
class WishDomainTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private TestUtil testUtil;

    private HttpHeaders authorizedHeaders;
    private List<Wish> wishes;

    @BeforeEach
    public void beforeEach() {
        //모든 테스트는 user@example.com 사용자가 로그인됨을 기반으로 한다.
        authorizedHeaders = testUtil.getAuthorizedHeader(restTemplate, port);

        //모든 테스트는 3개의 위시리스트가 등록된 상태로 진행한다.
        for (var req: List.of(
            new WishRequest(1L, 2L),
            new WishRequest(2L, 4L),
            new WishRequest(3L, 5L))
        ) {
            //add API 호출로 위시리스트를 하나씩 등록
            restTemplate.exchange(
                new RequestEntity<>(req, authorizedHeaders, HttpMethod.POST, testUtil.getUri(port, "/api/wishes")),
                String.class);
        }
        //등록한 위시리스트를 저장해둔다.
        wishes = wishRepository.findWishesByMember(testUtil.getAuthorizedMember());
        wishes.sort(Comparator.comparing(w -> w.getProduct().getId()));
        System.out.println("wishes = " + wishes);
    }

    @Test
    @DisplayName("API test: get wishlist")
    void getWishlist() {
        //given: BeforeEach 메서드에서 등록한 위시리스트가 주어짐

        //when: (/api/wishes로 GET 요청 보내기)
        var response = restTemplate.exchange(
            new RequestEntity<>(null, authorizedHeaders, HttpMethod.GET, testUtil.getUri(port, "/api/wishes")),
            WishListApiResponse.class
        );

        //then: given으로 등록한 위시리스트가 잘 얻어와지는지 확인
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        List<WishResponse> actual = response.getBody().getWishlist();
        actual.sort(Comparator.comparing(WishResponse::productId));
        System.out.println("actual = " + actual);
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i))
                .isEqualTo(WishResponse.of(wishes.get(i).getQuantity(), wishes.get(i).getProduct()));
        }
    }

    @Test
    @DisplayName("API test: add wish")
    void addWish() {
        //given: 3번의 request 요청이 순서대로 수행됨
        WishRequest[] request = {
            new WishRequest(4L, 2L),
            new WishRequest(4L ,3L),
            new WishRequest(4L, -6L)
        };
        //각 request 요청과 대응되어야 하는 response
        WishAddResponse[] expected = {
            new WishAddResponse("create", 2L),
            new WishAddResponse("add", 5L),
            new WishAddResponse("delete", 0L)
        };

        for (int i = 0; i < 3; i++) {
            //when
            var actualResponse = restTemplate.exchange(
                new RequestEntity<>(request[i], authorizedHeaders, HttpMethod.POST, testUtil.getUri(port, "/api/wishes")),
                WishAddApiResponse.class);

            //then
            assertThat(actualResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
            assertThat(Objects.requireNonNull(actualResponse.getBody()).getResult())
                .isEqualTo(expected[i]);
        }
    }

    @Test
    @DisplayName("API test: update wish")
    void updateWish() {
        //given
        WishRequest request = new WishRequest(1L, 10L);

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(request, authorizedHeaders, HttpMethod.PUT, testUtil.getUri(port, "/api/wishes")),
            WishUpdateApiResponse.class);

        assertThat(actualResponse.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getResult().quantity())
            .isEqualTo(request.quantity());
    }

    @Test
    @DisplayName("API test: delete wish")
    void deleteWish() {
        //given
        Long idToDelete = 1L;
        WishDeleteRequest request = new WishDeleteRequest(idToDelete);

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(request, authorizedHeaders, HttpMethod.DELETE, testUtil.getUri(port, "/api/wishes")),
            WishUpdateApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(wishRepository.findWishesByMember(testUtil.getAuthorizedMember())
            .stream()
            .anyMatch(w -> w.getProduct().getId().equals(idToDelete))
        )
            .isFalse();
    }

    @Test
    @DisplayName("API fail test: product not found exception")
    void addWishException() {
        //given
        WishRequest request = new WishRequest(44444L, 4L);
        List<HttpMethod> methods = List.of(
            HttpMethod.POST,
            HttpMethod.PUT);

        for (var method: methods) {
            //when
            var actualResponse = restTemplate.exchange(
                new RequestEntity<>(request, authorizedHeaders, method, testUtil.getUri(port, "/api/wishes")),
                ErrorApiResponse.class);

            //then
            assertThat(actualResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(Objects.requireNonNull(actualResponse.getBody()).getMessage())
                .isEqualTo(new ProductNotFoundException().getMessage());
        }

    }

    @Test
    @DisplayName("API fail test: product not included exception")
    void deleteWishException() {
        //given
        List<Object> requests = List.of(
            new WishDeleteRequest(5L),
            new WishRequest(5L, 5L));
        List<HttpMethod> methods = List.of(
            HttpMethod.DELETE,
            HttpMethod.PUT);

        for (int i = 0; i < 2; i++) {
            //when
            var actualResponse = restTemplate.exchange(
                new RequestEntity<>(requests.get(i), authorizedHeaders, methods.get(i), testUtil.getUri(port, "/api/wishes")),
                ErrorApiResponse.class);

            //then
            assertThat(actualResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(Objects.requireNonNull(actualResponse.getBody()).getMessage())
                .isEqualTo(new ProductNotIncludedInWishlistException().getMessage());
        }
    }
}