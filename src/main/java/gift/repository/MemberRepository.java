package gift.repository;

import gift.domain.Member;
import gift.dto.MemberRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member save(MemberRequest memberRequest){
        String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, memberRequest.getEmail());
            ps.setString(2, memberRequest.getPassword());
            return ps;
        }, keyHolder);

        return new Member(keyHolder.getKey().longValue(), memberRequest.getEmail(), memberRequest.getPassword());
    }
    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
                return new Member(rs.getLong("id"), rs.getString("email"), rs.getString("password"));
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void delete(Long id){
        jdbcTemplate.update("DELETE FROM member WHERE id = ?", id);
    }
}
