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
        assertThat(member.isEmailMatching("test@example.com")).isTrue();
        assertThat(member.isPasswordMatching("password")).isTrue();
    }

    @Test
    @DisplayName("Member 모델 업데이트 테스트")
    public void testUpdateMember() {
        Member member = new Member(1L, "test@example.com", "password");
        member.update("new@example.com", "newpassword");

        assertThat(member.isEmailMatching("new@example.com")).isTrue();
        assertThat(member.isPasswordMatching("newpassword")).isTrue();
    }

    @Test
    @DisplayName("Member 모델 이메일 매칭 테스트")
    public void testEmailMatching() {
        Member member = new Member(1L, "test@example.com", "password");

        assertThat(member.isEmailMatching("test@example.com")).isTrue();
        assertThat(member.isEmailMatching("wrong@example.com")).isFalse();
    }

    @Test
    @DisplayName("Member 모델 비밀번호 매칭 테스트")
    public void testPasswordMatching() {
        Member member = new Member(1L, "test@example.com", "password");

        assertThat(member.isPasswordMatching("password")).isTrue();
        assertThat(member.isPasswordMatching("wrongpassword")).isFalse();
    }

    @Test
    @DisplayName("Member 모델 ID 매칭 테스트")
    public void testIdMatching() {
        Member member = new Member(1L, "test@example.com", "password");

        assertThat(member.isIdMatching(1L)).isTrue();
        assertThat(member.isIdMatching(2L)).isFalse();
    }
}
