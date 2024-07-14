package gift.EntityTest;

import gift.Model.Member;
import gift.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void CreationTest(){
        Member member = new Member("woo6388@naver.com", "123456789");
        Member actual = memberRepository.save(member);

        assertAll(
                ()->assertThat(actual.getEmail()).isEqualTo("woo6388@naver.com"),
                () -> assertThat(actual.getPassword()).isEqualTo("123456789")
        );
    }

    @Test
    void SetterTest(){
        Member member = new Member("woo6388@naver.com", "123456789");
        Member actual = memberRepository.save(member);

        actual.setEmail("qoo6388@naver.com");
        actual.setPassword("0000");

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo("qoo6388@naver.com"),
                () -> assertThat(actual.getPassword()).isEqualTo("0000")
        );

    }

}
