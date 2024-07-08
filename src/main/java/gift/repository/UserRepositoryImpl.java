package gift.repository;

import gift.domain.User;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(email,password) VALUES (?,?)";
        jdbcTemplate.update(sql,user.getEmail(),user.getPassword());
    }

    @Override
    public Optional<User> findByPasswordAndEmail(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? And password = ?";
        try {
            User user = jdbcTemplate.queryForObject(
                sql,
                (result,rowNum) -> new User(
                    result.getLong("id"),
                    result.getString("email"),
                    result.getString("password")
                )
                , email
                , password
            );
            return Optional.ofNullable(user);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    @Override
    public Optional<User> findByEmail(String email) {
        var sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
                )
                ,email
            );
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
