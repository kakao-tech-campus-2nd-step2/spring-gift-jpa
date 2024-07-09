package gift.dao;

import gift.member.dao.MemberRepository;
import gift.member.dto.MemberDto;
import gift.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Member member = new Member("newuser@email.com", "new!@#");
        Member savedMember = memberRepository.save(member);

        Member foundMember = memberRepository.findById(savedMember.getId())
                .orElse(null);

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getPassword()).isEqualTo(savedMember.getPassword());
    }

    @Test
    @DisplayName("회원 ID 조회 실패 테스트")
    void findByIdFailed() {
        Member member = new Member("newuser@email.com", "new!@#");
        memberRepository.save(member);

        Member foundMember = memberRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundMember).isNull();
    }

    @Test
    @DisplayName("회원 이메일 조회 테스트")
    void findByEmail() {
        String email = "test@email.com";
        Member member = new Member(email, "password");
        memberRepository.save(member);

        Member foundMember = memberRepository.findByEmail(email)
                .orElse(null);

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원 이메일 조회 실패 테스트")
    void findByEmailFailed() {
        String email = "test@example.com";
        Member member = memberRepository.findByEmail(email)
                .orElse(null);

        assertThat(member).isNull();
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMember() {
        Member member = new Member("user@email.com", "user!@#");
        Member savedMember = memberRepository.save(member);
        savedMember.update(new MemberDto("updateuser@email.com", "update!@#"));

        Member updatedMember = memberRepository.save(savedMember);

        Member foundMember = memberRepository.findById(updatedMember.getId())
                .orElse(null);
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getPassword()).isEqualTo(savedMember.getPassword());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMember() {
        Member member = new Member("deleteuser@email.com", "delete!@#");
        Member savedMember = memberRepository.save(member);

        memberRepository.deleteById(savedMember.getId());

        boolean exists = memberRepository.existsById(savedMember.getId());
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("회원 이메일 중복 테스트")
    void checkDuplicateEmail() {
        memberRepository.save(new Member(
                "unique@email.com",
                "unique"
        ));
        Member duplicateMember = new Member("unique@email.com", "duplicate");

        Assertions.assertThatThrownBy(() -> memberRepository.save(duplicateMember))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}