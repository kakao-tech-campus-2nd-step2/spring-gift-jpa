package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        Member expected = new Member("testEmail", "testPassword", "testRole");
        Member actual = memberRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        ); 
    }

    @Test
    void findById(){

        Member expected = new Member("testEmail", "testPassword", "testRole");
        memberRepository.save(expected);

        long memberId = 1L;
        Optional<Member> member = memberRepository.findById(memberId);

        assertAll(
            () -> assertThat(member).isPresent(),
            () -> assertThat(member.get().getId()).isEqualTo(memberId)
        );
    }

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
