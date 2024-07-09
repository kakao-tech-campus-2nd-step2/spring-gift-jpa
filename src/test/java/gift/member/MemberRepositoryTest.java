package gift.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS member");
        jdbcTemplate.execute(
            """
                CREATE TABLE member
                (
                    email    VARCHAR(255) PRIMARY KEY,
                    password VARCHAR(255)
                )
                """
        );
    }

    @Test
    @DisplayName("[Unit] addMemberTest")
    void addMemberTest() {
        //given
        Member expect = new Member("aaa@email.com", "password");

        //when
        memberRepository.addMember(expect);
        Member actual = jdbcTemplate.queryForObject(
            """
                SELECT * FROM MEMBER WHERE EMAIL=?
                """,
            (rs, rowNum) -> new Member(
                rs.getString("EMAIL"),
                rs.getString("PASSWORD")
            ),
            expect.email()
        );

        //then
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    @DisplayName("[Unit] existMemberByEmail test")
    void existMemberByEmailTest() {
        //given
        Member expect = new Member("aaa@email.com", "password");
        memberRepository.addMember(expect);

        //when
        Boolean trueCase = memberRepository.existMemberByEmail("aaa@email.com");
        Boolean falseCase = memberRepository.existMemberByEmail("bbb@email.com");

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
        memberRepository.addMember(expect);

        //when
        Member actual = memberRepository.findMemberByEmail("aaa@email.com");
        Throwable errorCase = catchThrowable(
            () -> memberRepository.findMemberByEmail("bbb@email.com")
        );

        //then
        assertAll(
            () -> assertThat(actual).isEqualTo(expect),
            () -> assertThat(errorCase).isInstanceOf(EmptyResultDataAccessException.class)
        );
    }
}