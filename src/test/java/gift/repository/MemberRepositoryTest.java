package gift.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Member;
import gift.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    Member member = new Member("aaa123@2a.com", "1234");

    @Test
    void insert() {
        assertThat(member.getId()).isNull();
        Member savedMember = memberRepository.save(member);
        assertThat(savedMember.getId()).isEqualTo(1L);
        assertThat(savedMember.getRole()).isEqualTo(Role.ROLE_USER);
    }

    @Test
    void getMemberByEmail() {
        memberRepository.save(member);
        Member findMember = memberRepository.findByEmail("aaa123@2a.com");
        assertThat(findMember.getEmail()).isEqualTo("aaa123@2a.com");
    }


}