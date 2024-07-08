package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.entity.Member;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    @Test
    void save() {
        Member expected = new Member("testEmail", "testPassword", "testRole");
        Member actual = memberRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        ); 
    }

    @Transactional
    @Test
    void findById() {
    
        Member expected = new Member("testEmail", "testPassword", "testRole");
        expected.setId(1L);
        memberRepository.save(expected);

        Optional<Member> member = memberRepository.findById(expected.getId());

        assertAll(
            () -> assertThat(member).isPresent(),
            () -> assertThat(member.get().getId()).isEqualTo(expected.getId())
        );
    }
    
    @Transactional
    @Test
    void delete(){
        
        Member expected = new Member("testEmail", "testPassword", "testRole");
        memberRepository.save(expected);

        long memberId = 1L;
        memberRepository.deleteById(memberId);

        Optional<Member> member = memberRepository.findById(memberId);
        assertThat(member).isNotPresent();
    }
}
