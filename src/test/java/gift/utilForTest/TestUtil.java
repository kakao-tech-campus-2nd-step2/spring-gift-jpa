package gift.utilForTest;

import gift.domain.controller.apiResponse.MemberRegisterApiResponse;
import gift.domain.dto.request.MemberRequest;
import gift.domain.entity.Member;
import gift.domain.exception.MemberNotFoundException;
import gift.domain.repository.MemberRepository;
import java.net.URI;
import java.util.Objects;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TestUtil {

    private final MemberRepository memberRepository;
    private final String memberEmail = "test@example.com";

    private HttpHeaders headers = null;
    private Member authorizedMember = null;

    public TestUtil(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public URI getUri(Integer port, String path, Object... pathVariables) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path(path)
            .build(pathVariables);
    }

    public HttpHeaders getAuthorizedHeader(TestRestTemplate restTemplate, Integer port) {
        if (headers != null) {
            return headers;
        }

        ResponseEntity<MemberRegisterApiResponse> response = restTemplate.exchange(
            new RequestEntity<>(
                new MemberRequest(memberEmail, "test"),
                new HttpHeaders(),
                HttpMethod.POST,
                getUri(port, "/api/members/register")),
            MemberRegisterApiResponse.class);

        headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(response.getBody()).getToken());
        return headers;
    }

    public Member getAuthorizedMember() {
        if (authorizedMember != null) {
            return authorizedMember;
        }

        authorizedMember = memberRepository.findByEmail(memberEmail).orElseThrow(MemberNotFoundException::new);
        return authorizedMember;
    }
}
