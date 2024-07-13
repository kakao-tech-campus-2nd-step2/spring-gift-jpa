package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.member.dto.MemberRequest;
import gift.domain.member.entity.Member;
import gift.domain.wishlist.dto.ProductIdRequest;
import gift.domain.wishlist.dto.WishRequest;
import gift.domain.member.service.MemberService;
import gift.domain.wishlist.service.WishService;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
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


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WishControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WishService wishService;

    private String token;

    @BeforeEach
    public void beforeEach() {
        // 회원 가입
        MemberRequest memberRequest = new MemberRequest("test@google.co.kr", "password");
        token = memberService.register(memberRequest);
    }

    @Test
    void getWishesTest() {
        // given
        var url = "http://localhost:" + port + "/api/wishes";
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        var requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));

        // when
        var actual = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

        Member member = memberService.getMemberFromToken(token);
        memberService.deleteMember(member.getId());
    }

    @Test
    void createWishTest() {
        // given
        var request = new ProductIdRequest(1L);

        var url = "http://localhost:" + port + "/api/wishes";
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        var requestEntity = new RequestEntity<>(request, headers, HttpMethod.POST, URI.create(url));

        // when
        var actual = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        wishService.deleteWish(1L, memberService.getMemberFromToken(token));
        Member member = memberService.getMemberFromToken(token);
        memberService.deleteMember(member.getId());
    }

    @Test
    void deleteWishTest() {
        // given
        // 테스트를 위한 wish 1개 생성
        var request = new WishRequest(memberService.getMemberFromToken(token).getId(), 1L);
        wishService.addWish(request);

        var id = 1L;
        var url = "http://localhost:" + port + "/api/wishes/" + id;
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        var requestEntity = new RequestEntity<>(headers, HttpMethod.DELETE, URI.create(url));

        // when
        var actual = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}