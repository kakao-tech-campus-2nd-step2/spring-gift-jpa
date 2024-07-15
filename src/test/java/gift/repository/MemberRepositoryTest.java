package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.entity.Member;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
        memberRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void save() {
        Member expected = new Member("12345@12345.com", "1", "홍길동", "default_user");
        Member actual = memberRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("홍길동"),
                () -> assertThat(actual.getEmail()).isEqualTo("12345@12345.com"),
                () -> assertThat(actual.getPassword()).isEqualTo("1"),
                () -> assertThat(actual.getRole()).isEqualTo("default_user")
        );
    }

    @Test
    @DisplayName("모든 유저 불러오기")
    void findAll(){
        memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user"));
        memberRepository.save(new Member("22345@12345.com", "2", "홍길동", "default_user"));

        Member expected1 = new Member("12345@12345.com", "1", "홍길동", "default_user");
        Member expected2 = new Member("22345@12345.com", "2", "홍길동", "default_user");

        List<Member> expectedList = new ArrayList<>();

        expectedList.add(expected1);
        expectedList.add(expected2);


        List<Member> actualList = memberRepository.findAll();

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("조건에 맞는 유저 수 세기 (db 무결성 검증용)")
    void countByEmail(){
        memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user"));
        memberRepository.save(new Member("22345@12345.com", "2", "홍길동", "default_user"));
        assertThat(memberRepository.countByEmail("12345@12345.com")).isEqualTo(1);
    }

    @Test
    @DisplayName("이메일로 한명 찾기")
    void findOne(){
        memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user"));
        memberRepository.save(new Member("22345@12345.com", "2", "홍길동", "default_user"));

        Member expected = new Member("22345@12345.com", "2", "홍길동", "default_user");

        Member actual = memberRepository.findByEmail("22345@12345.com").get();

        assertThat(actual).isEqualTo(expected);
    }


}