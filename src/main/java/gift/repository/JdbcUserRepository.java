package gift.repository;

import gift.entity.User;
import gift.exceptionhandler.DatabaseAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> isExistUser(User user) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND type = ?";
        String email = user.getEmail();
        String password = user.getPassword();
        String type = user.getType();
        List<User> users = jdbcTemplate.query(sql, new Object[]{email, password, type}, (rs, rowNum) -> new User(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("type")
        ));

        if (users.size() > 0) return Optional.of(users.get(0));
        return Optional.empty();

    }

    @Override
    public Boolean isExistEmail(String email) {
        String sql = "select * from users where email = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{email}, (rs, rowNum) -> new User(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("type")
        ));

        if (users.size() > 0) return true;
        return false;
    }

    @Override
    public Boolean save(User user){
        try {
            String sql = "insert into users (email, password, type) values (?, ?, ?)";
            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getType());
        }catch(Exception e) {
            throw new DatabaseAccessException("중복된 email");
        }
        return true;
    }

    @Override
    public List<User> findAll(){
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    User user = new User(
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("type")
                    );
                    return user;
                });
        return users;
    }


}
