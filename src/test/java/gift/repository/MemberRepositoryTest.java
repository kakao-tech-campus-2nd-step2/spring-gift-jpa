package gift.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Member;
import gift.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    Member member = new Member("aaa123@2a.com", "1234");

    @Test
    @DisplayName("Member insert 테스트")
    void insert() {
        assertThat(member.getId()).isNull();
        Member savedMember = memberRepository.save(member);
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getRole()).isEqualTo(Role.ROLE_USER);

        member.setRole(Role.ROLE_ADMIN);
        Member savedAdminMember = memberRepository.save(member);
        assertThat(savedMember.getRole()).isEqualTo(Role.ROLE_ADMIN);
    }

    @Test
    @DisplayName("Member findByEmail 테스트")
    void getMemberByEmail() {
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findByEmail(savedMember.getEmail()).get();

        assertThat(findMember).isEqualTo(savedMember);
    }


}