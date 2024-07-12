package gift.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    @DisplayName("Member 모델 생성 테스트")
    public void testCreateMember() {
        Member member = new Member(1L, "test@example.com", "password");

        assertThat(member.getId()).isEqualTo(1L);
        assertThat(member.getEmail()).isEqualTo("test@example.com");
        assertThat(member.getPassword()).isEqualTo("password");
    }

    @Test
    @DisplayName("Member 모델 업데이트 테스트")
    public void testUpdateMember() {
        Member member = new Member(1L, "test@example.com", "password");
        member.update("new@example.com", "newpassword");

        assertThat(member.getEmail()).isEqualTo("new@example.com");
        assertThat(member.getPassword()).isEqualTo("newpassword");
    }
}
