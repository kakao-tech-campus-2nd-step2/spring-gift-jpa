package gift.repository;

import gift.entity.Member;
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
    void findMemberByEmailAndPassword() {
        //GIVEN
        Member member = new Member(EMAIL, PASSWORD);
        memberRepository.save(member);

        //WHEN
        Optional<Member> foundMember = memberRepository.findMemberByEmailAndPassword(EMAIL, PASSWORD);

        //THEN
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo(EMAIL);
        assertThat(foundMember.get().getId()).isPositive();
    }

    @Test
    void findByEmail() {
        //GIVEN
        Member member = new Member(EMAIL, PASSWORD);
        Long savedMemberId = memberRepository.save(member).getId();

        //WHEN
        Optional<Member> foundMember = memberRepository.findByEmail(EMAIL);

        //THEN
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getId()).isEqualTo(savedMemberId);
    }

}
