package gift.member.dao;

import gift.member.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member save(Member member) {
        String sql = "INSERT INTO users (email, password) VALUES(?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, member.email());
            statement.setString(2, member.password());
            return statement;
        }, keyHolder);

        return new Member(
                keyHolder.getKey()
                         .longValue(),
                member.email(),
                member.password()
        );
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        return jdbcTemplate.query(sql,
                        (rs, rowNum) -> new Member(
                                rs.getLong("id"),
                                rs.getString("email"),
                                rs.getString("password")
                        ),
                        email
                )
                .stream()
                .findFirst();
    }

}
