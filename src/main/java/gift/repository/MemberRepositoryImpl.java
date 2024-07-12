package gift.repository;

import gift.model.Member;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        return member;
    };

    @Override
    public Member save(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO members (email, password) VALUES (?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            return ps;
        }, keyHolder);

        member.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return member;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        return jdbcTemplate.query(sql, memberRowMapper, email).stream().findFirst();
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        return jdbcTemplate.query(sql, memberRowMapper, id).stream().findFirst();
    }
}
