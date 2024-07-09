package gift.Repository;

import gift.Model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("userRepository")
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createTable() {
        String sql = """
                    CREATE TABLE if not exists Users (
                        id bigint auto_increment,
                        email VARCHAR(50) NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        password VARCHAR(50) NOT NULL,
                        isAdmin BOOLEAN default false,
                        PRIMARY KEY (id)
                    )
                """;
        jdbcTemplate.execute(sql);
        //관리자용 계정 생성
        sql = "INSERT INTO Users (email, name, password, isAdmin) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, "root@naver.com", "root", "root", true);

    }

    public void save(User user) {
        String sql = "INSERT INTO Users (email, name, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPassword());
    }

    public List<User> login(User user) {
        String sql = """
                    SELECT *
                    FROM Users
                    WHERE email = ? AND password = ?
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), user.getEmail(), user.getPassword());
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email);
        return users.isEmpty() ? null : users.get(0);
    }
}
