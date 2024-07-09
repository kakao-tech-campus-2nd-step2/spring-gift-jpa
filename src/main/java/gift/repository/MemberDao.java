package gift.repository;

import gift.domain.Member;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertMember(Member member) {
        String sql = "INSERT INTO members(email, password) values (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    public Optional<Member> selectMember(Member member) {
        String sql = "SELECT email,password FROM members WHERE email=? and password=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
            new Member(
                resultSet.getString("email"),
                resultSet.getString("password")
            ), member.getEmail(), member.getPassword()
        ));
    }

    public Long selectMemberIdByEmail(String email) {
        String sql = "SELECT id FROM members WHERE email=?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }
}
