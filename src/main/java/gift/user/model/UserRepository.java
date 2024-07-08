package gift.user.model;

import gift.user.model.dto.SignUpRequest;
import gift.user.model.dto.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int signUpUser(SignUpRequest signUpRequest, String salt) {
        var sql = "INSERT INTO AppUser (email,password,role,salt) VALUES (?, ?, ?,?)";
        Object[] params = new Object[]{signUpRequest.getEmail(), signUpRequest.getPassword(),
                signUpRequest.getRole(), salt};
        return jdbcTemplate.update(sql, params);
    }

    public User findByEmail(String email) {
        var sql = "SELECT * FROM AppUser WHERE email = ? AND is_active = true";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("salt")
                    ),
                    email
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // 결과가 없을 경우 null 반환
        }
    }

    public User findUser(Long id) {
        var sql = "SELECT * FROM AppUser WHERE id = ? AND is_active = true";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("salt")
                    ),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int updatePassword(Long id, String newPassword) {
        var sql = "UPDATE AppUser SET password = ? WHERE id = ? AND is_active = true";
        return jdbcTemplate.update(sql, newPassword, id);
    }

    public String findEmail(Long id) {
        var sql = "SELECT email FROM AppUser WHERE id ? AND is_active = true";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }
}