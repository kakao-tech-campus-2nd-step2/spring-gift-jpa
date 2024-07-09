package gift.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import gift.member.Role;
import gift.member.entity.Member;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        // 임의의 회원 3명
        List<Member> members = List.of(
                new Member("omg", "test1@test.com", "1234", Role.USER),
                new Member("abc", "test2@test.com", "1234", Role.USER),
                new Member("def", "test3@test.com", "1234", Role.USER)
        );

        memberRepository.saveAll(members);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 생성")
    void addMember() {
        //given
        Member member = new Member("wow", "abc123@test.com", "1234", Role.USER);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertAll(
                () -> assertNotNull(savedMember.getId()),
                () -> assertEquals(member.getName(), savedMember.getName()),
                () -> assertEquals(member.getEmail(), savedMember.getEmail()),
                () -> assertEquals(member.getPassword(), savedMember.getPassword()),
                () -> assertEquals(member.getRole(), savedMember.getRole())
        );
    }

    @Test
    @DisplayName("회원 조회")
    void findMember() {

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertAll(
                () -> assertEquals(3, members.size()),
                () -> assertEquals("omg", members.get(0).getName()),
                () -> assertEquals("abc", members.get(1).getName()),
                () -> assertEquals("def", members.get(2).getName())
        );

        // 존재하지 않는 회원 조회
        assertThrows(Exception.class,
                () -> memberRepository.findById(4L).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."))
        );
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteMember() {
        // when
        memberRepository.deleteById(1L);

        // then
        assertThrows(Exception.class,
                () -> memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."))
        );
    }
}