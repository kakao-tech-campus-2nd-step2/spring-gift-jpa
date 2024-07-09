package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

    private Member expected;
    private Member actual;

    @BeforeEach
    void setUp(){
        expected = new Member("testEmail", "testPassword", "testRole");
        actual = memberRepository.save(expected);
    }

    @Transactional
    @Test
    void save() {
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        ); 
    }

    @Transactional
    @Test
    void findByEmail() {
    
        Optional<Member> member = memberRepository.findByEmail("testEmail");

        assertAll(
            () -> assertThat(member).isPresent(),
            () -> assertThat(member.get().getId()).isEqualTo(expected.getId())
        );
    }
    
    @Transactional
    @Test
    void delete(){
        
        memberRepository.delete(expected);

        Optional<Member> member = memberRepository.findByEmail("testEmail");
        assertThat(member).isNotPresent();
    }
}
