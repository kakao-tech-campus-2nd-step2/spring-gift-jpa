package gift.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addMember(Member member) {
        jdbcTemplate.update(
            "INSERT INTO MEMBER(EMAIL, PASSWORD) VALUES (?, ? )",
            member.email(), member.password()
        );
    }

    public Boolean existMemberByEmail(String email) {
        return jdbcTemplate.queryForObject(
            "SELECT EXISTS (SELECT * FROM MEMBER WHERE email = ?)",
            Boolean.class,
            email
        );
    }

    public Member findMemberByEmail(String email) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM MEMBER WHERE email = ?",
            (rs, rowNum) -> new Member(
                rs.getString("EMAIL"),
                rs.getString("PASSWORD")
            ),
            email
        );
    }
}
