package gift.member.repository;

import gift.member.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void beforeEach() {
        member = new Member(null, MemberType.USER, new Email("email"), new Password("password"), new Nickname("nickname"));
    }

    @Test
    @Description("save 테스트")
    void saveTest() {
        // given
        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getMemberType()).isEqualTo(member.getMemberType());
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getNickname()).isEqualTo(member.getNickname());
    }

    @Test
    @Description("findById 테스트")
    void findByIdTest() {
        // given
        Member savedMember = memberRepository.save(member);

        // when
        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());

        // then
        assertThat(foundMember).contains(member);
    }

    @Test
    @Description("findAll 테스트")
    void findAll() {
        // given
        Member member1 = new Member(null, MemberType.USER, new Email("email1"), new Password("password1"), new Nickname("nickname1"));
        Member member2 = new Member(null, MemberType.USER, new Email("email2"), new Password("password2"), new Nickname("nickname2"));
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).contains(member1, member2);
    }

    @Test
    @Description("update 테스트")
    void updateTest() {
        // given
        Member savedMember = memberRepository.save(member);

        // when
        savedMember = new Member(savedMember.getId(), savedMember.getMemberType(), savedMember.getEmail(), savedMember.getPassword(), savedMember.getNickname());
        Member updateProduct = memberRepository.save(savedMember);

        // then
        assertThat(updateProduct).isEqualTo(savedMember);
    }

    @Test
    @Description("deleteById 테스트")
    void deleteTest() {
        // given
        Member savedMember = memberRepository.save(member);

        // when
        memberRepository.deleteById(savedMember.getId());
        Optional<Member> members = memberRepository.findById(member.getId());

        // then
        assertThat(members).isEmpty();
    }
}