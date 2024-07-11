package gift.product.controller;

import static org.assertj.core.api.Assertions.assertThat;

import gift.auth.dto.LoginReqDto;
import gift.auth.token.AuthToken;
import gift.common.exception.ErrorResponse;
import gift.common.exception.ValidationError;
import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import gift.product.exception.ProductErrorCode;
import gift.product.message.ProductInfo;
import gift.utils.RestPage;
import gift.utils.TestUtils;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("상품 컨트롤러 테스트")
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private String accessToken;

    @BeforeAll
    void setUp() {
        baseUrl = "http://localhost:" + port;

        var url = baseUrl + "/api/members/register";
        var reqBody = new LoginReqDto("productController@test.com", "1234");
        var requestEntity = new RequestEntity<>(reqBody, HttpMethod.POST, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, AuthToken.class);

        accessToken = actual.getBody().accessToken();

        // 상품 초기화
        var productUrl = baseUrl + "/api/products";
        List.of(
                new ProductReqDto("상품1", 1000, "keyboard.png"),
                new ProductReqDto("상품2", 2000, "mouse.png"),
                new ProductReqDto("상품3", 3000, "monitor.png")
        ).forEach(productReqDto -> {
            var productRequest = TestUtils.createRequestEntity(productUrl, productReqDto, HttpMethod.POST, accessToken);
            restTemplate.exchange(productRequest, String.class);
        });
    }

    @Test
    @DisplayName("전체 상품 조회")
    void 전체_상품_조회() {
        //given
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/products")
                .queryParam("page", 0)
                .queryParam("size", 3)
                .queryParam("sort", "id,desc")  // id 역순 정렬
                .build()
                .toString();

        RequestEntity<Object> request = TestUtils.createRequestEntity(url, null, HttpMethod.GET, accessToken);

        //when
        var responseType = new ParameterizedTypeReference<RestPage<ProductResDto>>() {};
        var actual = restTemplate.exchange(request, responseType);  // Page<ProductResDto> 타입으로 받음
        var products = actual.getBody().getContent();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(3);
        assertThat(products).map(ProductResDto::name).containsExactly("상품3", "상품2", "상품1");
        assertThat(products).map(ProductResDto::price).containsExactly(3000, 2000, 1000);

        products.forEach(product -> assertThat(product).isInstanceOf(ProductResDto.class));
    }

    @Test
    @DisplayName("단일 상품 조회")
    void 단일_상품_조회() {
        //given
        Long productId = 1L;
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, null, HttpMethod.GET, accessToken);

        //when
        var actual = restTemplate.exchange(request, ProductResDto.class);
        var product = actual.getBody();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(product).isNotNull();
        assertThat(product).isInstanceOf(ProductResDto.class);

        assertThat(product.id()).isEqualTo(productId);
        assertThat(product.name()).isEqualTo("상품1");
        assertThat(product.price()).isEqualTo(1000);
        assertThat(product.imageUrl()).isEqualTo("keyboard.png");
    }

    @Test
    @DisplayName("단일 상품 조회 실패")
    void 단일_상품_조회_실패() {
        //given
        Long productId = -1L;
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, null, HttpMethod.GET, accessToken);

        //when
        var actual = restTemplate.exchange(request, ErrorResponse.class);
        ErrorResponse errorResponse = actual.getBody();

        //then
        // {code='P001', status=404, message='상품 ID에 해당하는 상품을 찾을 수 없습니다.', invalidParams=null}

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse).isInstanceOf(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo(ProductErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
        assertThat(errorResponse.getInvalidParams()).isNull();
    }

    @Test
    @DisplayName("상품 추가")
    void 상품_추가() {
        //given
        var reqBody = new ProductReqDto("새로운 상품", 10000, "https://www.google.com/new-product.png");
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products", reqBody, HttpMethod.POST, accessToken);

        //when
        var actual = restTemplate.exchange(request, ProductResDto.class);
        var product = actual.getBody();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(product).isNotNull();
        assertThat(product).isInstanceOf(ProductResDto.class);

        assertThat(product.id()).isNotNull();
        assertThat(product.name()).isEqualTo("새로운 상품");
        assertThat(product.price()).isEqualTo(10000);
        assertThat(product.imageUrl()).isEqualTo("https://www.google.com/new-product.png");
    }

    @Test
    @DisplayName("상품 추가 실패")
    void 상품_추가_실패() {
        //given
        var reqBody = new ProductReqDto("카카오 상품@테스트 오류 입니다.", 10000, "https://www.google.com/new-product.png");
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products", reqBody, HttpMethod.POST, accessToken);

        //when
        var actual = restTemplate.exchange(request, ErrorResponse.class);
        ErrorResponse errorResponse = actual.getBody();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse).isInstanceOf(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo(ProductErrorCode.INVALID_INPUT_VALUE_PRODUCT.getCode());
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(ProductErrorCode.INVALID_INPUT_VALUE_PRODUCT.getMessage());

        List<ValidationError> invalidParams = errorResponse.getInvalidParams();
        assertThat(invalidParams).isNotNull();
        assertThat(invalidParams.size()).isEqualTo(3);
        assertThat(invalidParams.parallelStream().map(ValidationError::message)).containsExactlyInAnyOrder(
                ProductInfo.PRODUCT_NAME_SIZE,
                ProductInfo.PRODUCT_NAME_KAKAO,
                ProductInfo.PRODUCT_NAME_PATTERN
        );
    }

    @Test
    @DisplayName("상품 수정")
    void 상품_수정() {
        //given: 상품 조회 후 마지막 상품을 수정
        var lastProductRequest = TestUtils.createRequestEntity(baseUrl + "/api/products", null, HttpMethod.GET, accessToken);
        var lastProductResponse = restTemplate.exchange(lastProductRequest, new ParameterizedTypeReference<RestPage<ProductResDto>>() {});
        var lastProduct = lastProductResponse.getBody().getContent().getLast();
        Long productId = lastProduct.id();

        var reqBody = new ProductReqDto("이름 수정", 20000, "https://www.google.com/modify.png");
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, reqBody, HttpMethod.PUT, accessToken);

        //when
        var actual = restTemplate.exchange(request, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(ProductInfo.PRODUCT_UPDATE_SUCCESS);

        // 상품 수정 후 조회
        var getRequest = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, null, HttpMethod.GET, accessToken);
        var getActual = restTemplate.exchange(getRequest, ProductResDto.class);
        var product = getActual.getBody();

        assertThat(getActual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(product).isNotNull();
        assertThat(product).isInstanceOf(ProductResDto.class);

        assertThat(product.id()).isEqualTo(productId);
        assertThat(product.name()).isEqualTo("이름 수정");
        assertThat(product.price()).isEqualTo(20000);
        assertThat(product.imageUrl()).isEqualTo("https://www.google.com/modify.png");
    }

    @Test
    @DisplayName("상품 수정 실패")
    void 상품_수정_실패() {
        //given
        Long productId = 1L;
        var reqBody = new ProductReqDto("카카오 수정", 20000, "https://www.google.com/keyboard.png");
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, reqBody, HttpMethod.PUT, accessToken);

        //when
        var actual = restTemplate.exchange(request, ErrorResponse.class);
        ErrorResponse errorResponse = actual.getBody();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse).isInstanceOf(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo(ProductErrorCode.INVALID_INPUT_VALUE_PRODUCT.getCode());
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(ProductErrorCode.INVALID_INPUT_VALUE_PRODUCT.getMessage());

        List<ValidationError> invalidParams = errorResponse.getInvalidParams();
        assertThat(invalidParams).isNotNull();
        assertThat(invalidParams.size()).isEqualTo(1);
        assertThat(invalidParams.parallelStream().map(ValidationError::message)).containsExactlyInAnyOrder(
                ProductInfo.PRODUCT_NAME_KAKAO
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void 상품_삭제() {
        //given
        Long productId = 2L;    // 테스트는 순서를 고려하지 않으므로 다른 테스트에서 사용하지 않는 상품 ID를 사용
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, null, HttpMethod.DELETE, accessToken);

        //when
        var actual = restTemplate.exchange(request, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(ProductInfo.PRODUCT_DELETE_SUCCESS);
    }

    @Test
    @DisplayName("상품 삭제 실패")
    void 상품_삭제_실패() {
        //given
        Long productId = -1L;
        var request = TestUtils.createRequestEntity(baseUrl + "/api/products/" + productId, null, HttpMethod.DELETE, accessToken);

        //when
        var actual = restTemplate.exchange(request, ErrorResponse.class);
        ErrorResponse errorResponse = actual.getBody();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse).isInstanceOf(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo(ProductErrorCode.PRODUCT_NOT_FOUND.getCode());
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
        assertThat(errorResponse.getInvalidParams()).isNull();
    }
}