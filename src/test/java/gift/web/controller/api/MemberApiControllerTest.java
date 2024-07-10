package gift.web.controller.api;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.service.MemberService;
import gift.web.dto.request.member.CreateMemberRequest;
import gift.web.dto.response.member.CreateMemberResponse;
import gift.web.dto.response.member.ReadMemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
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

    }

    @Test
    void readWishProduct() {
    }

    @Test
    void updateWishProduct() {
    }

    @Test
    void deleteWishProduct() {
    }
}