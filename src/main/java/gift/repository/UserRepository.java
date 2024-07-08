package gift.repository;

import gift.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUser(User user){
        String sql = "insert into users (email,password) values (?,?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword());
    }

    public Optional<User> findByEmail(String email){
        String sql = "select * from users where email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper(), email);
        if(users.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }

    public boolean existUserByEmail(String email){
        String sql = "select * from users where email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper(), email);
        return users.size()>0;

    }
}
