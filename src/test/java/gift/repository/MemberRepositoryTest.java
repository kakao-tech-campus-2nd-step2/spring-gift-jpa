package gift.repository;

import gift.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    private static final String EMAIL = "zzoe2346@git.com";
    private static final String PASSWORD = "12345678";

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("이메일과 비밀번호로 멤버 찾기(로그인용)")
    void findMemberByEmailAndPassword() {
        //Given
        Member member = new Member(EMAIL, PASSWORD);
        memberRepository.save(member);

        //When
        Optional<Member> foundMember = memberRepository.findByEmailAndPassword(EMAIL, PASSWORD);

        //Then
        assertThat(foundMember).isPresent()
                .hasValueSatisfying(m -> {
                    assertThat(m.getEmail()).isEqualTo(EMAIL);
                    assertThat(m.getId()).isPositive();
                });
    }

    @Test
    @DisplayName("이메일로 멤버 찾기(이메일 중복 확인용)")
    void findByEmail() {
        //Given
        Member member = new Member(EMAIL, PASSWORD);
        Long savedMemberId = memberRepository.save(member).getId();

        //When
        Optional<Member> foundMember = memberRepository.findByEmail(EMAIL);

        //Then
        assertThat(foundMember).isPresent()
                .hasValueSatisfying(m ->
                        assertThat(m.getId()).isEqualTo(savedMemberId)
                );
    }

}
