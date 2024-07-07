package gift.repository;

import gift.domain.Member;
import gift.dto.AuthRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void insertMember(AuthRequest authRequest) {
        String sql = "INSERT INTO members (email, password) VALUES (:email, :password)";
        var params = new BeanPropertySqlParameterSource(authRequest);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public Optional<Member> selectMember(String email) {
        var sql = "SELECT email, password FROM members WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Member.class)));
    }
}
