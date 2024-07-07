package gift.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.member.MemberRequest;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberRequestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("유효한 회원 가입 요청")
    public void testRegisterMemberValid() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", "validpassword");
        ResponseEntity<MemberRequest> response = restTemplate.postForEntity("/api/members/register", memberDTO, MemberRequest.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("유효하지 않은 이메일로 회원 가입 요청")
    public void testRegisterMemberInvalidEmail() {
        MemberRequest memberDTO = new MemberRequest(null, "invalid-email", "validpassword");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/members/register", memberDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("email", "유효한 이메일 주소를 입력해야 합니다.");
    }

    @Test
    @DisplayName("빈 이메일로 회원 가입 요청")
    public void testRegisterMemberBlankEmail() {
        MemberRequest memberDTO = new MemberRequest(null, "", "validpassword");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/members/register", memberDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("email", "이메일은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 회원 가입 요청")
    public void testRegisterMemberInvalidPassword() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", "123");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/members/register", memberDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("password", "비밀번호는 최소 4자리 이상이어야 합니다.");
    }

    @Test
    @DisplayName("빈 비밀번호로 회원 가입 요청")
    public void testRegisterMemberBlankPassword() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", "");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/members/register", memberDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("password", "비밀번호는 필수 입력 항목입니다.");
    }
}
