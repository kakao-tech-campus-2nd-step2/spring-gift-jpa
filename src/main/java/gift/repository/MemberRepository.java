package gift.repository;

import gift.domain.member.Member;
import gift.domain.member.MemberRequest;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
        rs.getLong("id"),
        rs.getString("email"),
        rs.getString("password")
    );

    public Member findById(Long id) {
        String sql = "SELECT * FROM Member WHERE id = ?";
        return jdbcTemplate.queryForObject(
            sql,
            memberRowMapper,
            id
        );
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM Member WHERE email = ?";
        try {
            Member member = jdbcTemplate.queryForObject(
                sql,
                memberRowMapper,
                email
            );
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByEmailAndPassword(MemberRequest memberRequest) {
        String sql = "SELECT * FROM Member WHERE email = ? AND password = ?";
        try {
            Member member = jdbcTemplate.queryForObject(
                sql,
                memberRowMapper,
                memberRequest.email(),
                memberRequest.password()
            );
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Member insert(MemberRequest memberRequest) {
        String sql = "INSERT INTO Member (email, password) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, memberRequest.email());
            preparedStatement.setString(2, memberRequest.password());
            return preparedStatement;
        }, keyHolder);
        return new Member(Objects.requireNonNull(keyHolder.getKey()).longValue(), memberRequest.email(), memberRequest.password());
    }
}
