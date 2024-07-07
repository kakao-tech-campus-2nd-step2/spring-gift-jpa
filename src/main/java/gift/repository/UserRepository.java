package gift.repository;

import gift.domain.User;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void insertUser(User user){
        String sql = "insert into user_table (email, password, role) values (?, ?, ?)";
        jdbcClient.sql(sql)
            .params(user.getEmail(), user.getPassword(), user.getRole())
            .update();
    }

    public Optional<User> selectUserById(Long id){
        String sql = "select id, email, password, role from user_table where id = ?";
        return jdbcClient.sql(sql)
            .param(id)
            .query(User.class)
            .optional();
    }

    public Optional<User> selectUserByEmail(String email){
        String sql = "select id, email, password, role from user_table where email = ?";
        return jdbcClient.sql(sql)
            .param(email)
            .query(User.class)
            .optional();
    }
}
