package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.entityForJpa.Member;
import gift.entityForJpa.Wish;
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
    @DisplayName("UserDao의 insertUser 메서드에 대응")
    void save() {
        Member expected = new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>());
        Member actual = memberRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("홍길동"),
                () -> assertThat(actual.getEmail()).isEqualTo("12345@12345.com"),
                () -> assertThat(actual.getPassword()).isEqualTo("1"),
                () -> assertThat(actual.getRole()).isEqualTo("default_user"),
                () -> assertThat(actual.getWishList()).isNotNull()
        );
    }

    @Test
    @DisplayName("모든 유저 불러오는 신규 메서드")
    void findAll(){
        memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>()));
        memberRepository.save(new Member("22345@12345.com", "2", "홍길동", "default_user", new ArrayList<>()));

        Member expected1 = new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>());
        Member expected2 = new Member("22345@12345.com", "2", "홍길동", "default_user", new ArrayList<>());

        List<Member> expectedList = new ArrayList<>();

        expectedList.add(expected1);
        expectedList.add(expected2);


        List<Member> actualList = memberRepository.findAll();

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("UserDao의 countUser 메서드에 대응")
    void countByEmail(){
        memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>()));
        memberRepository.save(new Member("22345@12345.com", "2", "홍길동", "default_user", new ArrayList<>()));
        assertThat(memberRepository.countByEmail("12345@12345.com")).isEqualTo(1);
    }

    @Test
    @DisplayName("UserDao의 getUser 메서드에 대응")
    void findOne(){
        memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>()));
        memberRepository.save(new Member("22345@12345.com", "2", "홍길동", "default_user", new ArrayList<>()));

        Member expected = new Member("22345@12345.com", "2", "홍길동", "default_user", new ArrayList<>());

        Member actual = memberRepository.findByEmail("22345@12345.com");

        assertThat(actual).isEqualTo(expected);
    }


}