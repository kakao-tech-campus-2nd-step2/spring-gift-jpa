package gift.api.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    private final String EMAIL = "kakao@kakao.com";
    private final String PASSWORD = "passWd12";
    private final Role ROLE = Role.ADMIN;

    @Test
    void save() {
        MemberRequest memberRequest = new MemberRequest(EMAIL, PASSWORD, ROLE);
        Member expected = new Member(memberRequest);
        Member actual = memberRepository.save(expected);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @Test
    void existsByEmail() {
        boolean expected = true;
        memberRepository.save(new Member(new MemberRequest(EMAIL, PASSWORD, ROLE)));
        boolean actual = memberRepository.existsByEmail(EMAIL);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void existsByEmailAndPassword() {
        boolean expected = true;
        memberRepository.save(new Member(new MemberRequest(EMAIL, PASSWORD, ROLE)));
        boolean actual = memberRepository.existsByEmailAndPassword(EMAIL, PASSWORD);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByEmail() {
        String expected = EMAIL;
        memberRepository.save(new Member(new MemberRequest(expected, PASSWORD, ROLE)));
        String actual = memberRepository.findByEmail(expected).get().getEmail();
        assertThat(actual).isEqualTo(expected);
    }
}