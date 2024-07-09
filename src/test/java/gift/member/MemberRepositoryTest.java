package gift.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("[Unit] addMemberTest")
    void addMemberTest() {
        //given
        Member expect = new Member("aaa@email.com", "password");

        //when
        memberRepository.save(expect);
        Member actual = memberRepository.findById(expect.getEmail()).get();

        //then
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    @DisplayName("[Unit] existMemberByEmail test")
    void existMemberByEmailTest() {
        //given
        Member expect = new Member("aaa@email.com", "password");
        memberRepository.save(expect);

        //when
        boolean trueCase = memberRepository.existsById("aaa@email.com");
        boolean falseCase = memberRepository.existsById("bbb@email.com");

        //then
        assertAll(
            () -> assertThat(trueCase).isTrue(),
            () -> assertThat(falseCase).isFalse()
        );
    }

    @Test
    @DisplayName("[Unit] findMemberByEmail test")
    void findMemberByEmailTest() {
        //given
        Member expect = new Member("aaa@email.com", "password");
        memberRepository.save(expect);

        //when
        Member actual = memberRepository.findById("aaa@email.com").get();
        Optional<Member> errorCase = memberRepository.findById("bbb@email.com");

        //then
        assertAll(
            () -> assertThat(actual).isEqualTo(expect),
            () -> assertThat(errorCase).isEmpty()
        );
    }
}