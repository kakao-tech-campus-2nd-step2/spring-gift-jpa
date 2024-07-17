package gift.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.controller.apiResponse.MemberLoginApiResponse;
import gift.domain.controller.apiResponse.MemberRegisterApiResponse;
import gift.domain.dto.request.MemberRequest;
import gift.utilForTest.TestUtil;
import java.util.Objects;
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
class MemberDomainTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestUtil testUtil;

    @Test
    @DisplayName("API test: register Member")
    void registerMember() {
        //given
        MemberRequest request = new MemberRequest("test@example.com", "test");

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(request, new HttpHeaders(), HttpMethod.POST, testUtil.getUri(port, "/api/members/register")),
            MemberRegisterApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getToken()).isNotNull();
    }

    @Test
    @DisplayName("API test: login Member")
    void loginMember() {
        //given
        MemberRequest request = new MemberRequest("test2@example.com", "test");
        restTemplate.exchange(
            new RequestEntity<>(request, new HttpHeaders(), HttpMethod.POST, testUtil.getUri(port, "/api/members/register")),
            MemberRegisterApiResponse.class);

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(request, new HttpHeaders(), HttpMethod.POST, testUtil.getUri(port, "/api/members/login")),
            MemberLoginApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getToken()).isNotNull();
    }
}