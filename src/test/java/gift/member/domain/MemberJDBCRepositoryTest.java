package gift.member.domain;

import gift.exception.type.ForbiddenException;
import gift.member.infrastructure.MemberJDBCRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MemberJDBCRepositoryTest {

    @Autowired
    private MemberJDBCRepository memberRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        email = "test@example.com";
        password = "password";
        jdbcTemplate.execute("DELETE FROM member");
    }

    @Test
    public void 모든_회원_조회_테스트() {
        // Given
        Member member1 = new Member(email, password);
        Member member2 = new Member("test2@example.com", "password2");
        memberRepository.join(member1);
        memberRepository.join(member2);

        // When
        List<Member> members = memberRepository.findAll();

        // Then
        assertThat(members).hasSize(2);
        assertThat(members.get(0).getEmail()).isEqualTo(email);
        assertThat(members.get(1).getEmail()).isEqualTo("test2@example.com");
    }

    @Test
    public void 이메일로_회원_조회_테스트() {
        // Given
        Member member = new Member(email, password);
        memberRepository.join(member);
        Member savedMember = memberRepository.findAll().get(0);

        // When
        Optional<Member> foundMember = memberRepository.findByEmail(savedMember.getEmail());

        // Then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo(email);
    }

    @Test
    public void 이메일로_회원_조회_실패_테스트() {
        // Given
        String nonExistentEmail = "nonexistent@example.com";

        // When
        Optional<Member> foundMember = memberRepository.findByEmail(nonExistentEmail);

        // Then
        assertThat(foundMember).isNotPresent();
    }

    @Test
    public void 회원가입_테스트() {
        // Given
        Member member = new Member(email, password);

        // When
        memberRepository.join(member);

        // Then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    public void 회원_로그인_테스트() {
        // Given
        Member member = new Member(email, password);
        memberRepository.join(member);

        // When
        String loginEmail = memberRepository.login(member);

        // Then
        assertThat(loginEmail).isEqualTo(email);
    }

    @Test
    public void 회원_로그인_실패_테스트() {
        // Given
        Member member = new Member(email, password);
        memberRepository.join(member);
        Member invalidMember = new Member(email, "wrongpassword");

        // When & Then
        assertThrows(ForbiddenException.class, () -> memberRepository.login(invalidMember));
    }

    @Test
    public void 회원_정보_수정_테스트() {
        // Given
        Member member = new Member(email, password);
        memberRepository.join(member);
        String newPassword = "newpassword";
        Member updatedMember = new Member(email, newPassword);

        // When
        memberRepository.update(updatedMember);

        // Then
        Optional<Member> foundMember = memberRepository.findByEmail(email);
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void 회원_삭제_테스트() {
        // Given
        Member member = new Member(email, password);
        memberRepository.join(member);
        Member savedMember = memberRepository.findAll().get(0);

        // When
        memberRepository.delete(savedMember.getEmail());

        // Then
        List<Member> members = memberRepository.findAll();
        assertThat(members).isEmpty();
    }
}
