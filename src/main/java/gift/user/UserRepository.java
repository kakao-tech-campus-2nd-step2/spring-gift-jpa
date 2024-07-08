package gift.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Users")
                .usingGeneratedKeyColumns("id");
    }

    public User insertUser(UserDTO user){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("nickname", user.getNickname());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new User(id, user.getEmail(), user.getPassword(), user.getNickname());
    }
    public Optional<User> selectUser(Long id) {
        var sql = "select id, email, password, nickname from Users where id = ?";
        User user = jdbcTemplate.queryForObject(
                sql,
                getUserMapper(),
                id
        );
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        var sql = "select id, email, password, nickname from Users where email = ?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(
                    sql,
                    getUserMapper(),
                    email
            );
        }
        catch (EmptyResultDataAccessException ex){
            throw ex;
        }
        return Optional.ofNullable(user);
    }

    public List<User> selectUsers(){
        var sql = "select id, email, password, nickname from Users";
        return jdbcTemplate.query(
                sql,
                getUserMapper()
        );
    }


    private static RowMapper<User> getUserMapper() {
        return (resultSet, rowNum) -> new User(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("nickname")
        );
    }

    public User updateUser(Long id, UserDTO updateParam){
        var sql = "update Users set password=?, nickname=? where id = ?";
        jdbcTemplate.update(sql,
                updateParam.getPassword(),
                updateParam.getNickname(),
                id);
        return new User(id, updateParam.getEmail(), updateParam.getPassword(), updateParam.getNickname());
    }

    public void deleteUser(Long id){
        var sql = "delete from Users where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
