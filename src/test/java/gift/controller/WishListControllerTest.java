package gift.controller;

import gift.dto.LoginMemberToken;
import gift.dto.MemberDTO;
import gift.dto.WishListDTO;
import java.util.HashMap;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WishListControllerTest {

    @LocalServerPort
    private int port;
    WebTestClientHelper webClient;

    @BeforeEach
    void setUp() {
        webClient = new WebTestClientHelper(port);
    }

    @Test
    @DisplayName("위시 리스트 아이템 추가")
    void addWishList() {
        //given
        String email = "abec";
        String password = "abecdddd";
        LoginMemberToken loginMemberToken = registerAndLogin(email, password);
        webClient.moreAction().post().uri("api/wishlist")
            .header("Authorization", loginMemberToken.getToken())
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(new WishListDTO(0L, 123L, 1)))
            .exchange().expectStatus().isOk();
    }


    @Test
    @DisplayName("위시 리스트 조회")
    void getWishList() {

    }

    @Test
    @DisplayName("위시 리스트 아이템 수량 변경")
    void updateWishList() {

    }

    @Test
    @DisplayName("위시 리스트 아이템 삭제")
    void deleteWishList() {

    }

    private LoginMemberToken registerAndLogin(String email, String password) {
        //register
        MemberDTO memberDTO = new MemberDTO(email, password, null);
        webClient.put("/api/member", memberDTO);

        //login
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("email", memberDTO.getEmail());
        userInfo.put("password", memberDTO.getPassword());
        String uri = webClient.uriMakeUseParameters("/api/member/login", userInfo);
        String token = Objects.requireNonNull(
            webClient.moreAction().get().uri(uri).accept(MediaType.APPLICATION_JSON).exchange()
                .expectBody(LoginMemberToken.class).returnResult().getResponseBody()).getToken();
        return new LoginMemberToken(token);
    }

}