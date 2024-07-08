package gift.dao;

import gift.domain.User;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createUserTable() {
        String sql = """
              CREATE TABLE users(
              id bigint AUTO_INCREMENT,
              email varchar(255),
              password varchar(255),
              primary key(id)
            );
              """;

        jdbcTemplate.execute(sql);
    }

    public void signUp(User user) {
        String sql = "INSERT INTO users(email,password) values(?,?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword());
    }

    public String signIn(User user) {
        String sql = "SELECT email FROM users WHERE email=? and password=?";
        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) ->
                resultSet.getString("email")
            , user.getEmail(), user.getPassword());
    }

    public boolean userEmailCheck(String email){
        String sql = "SELECT email FROM users WHERE email=?";

        List<String> DuplicatedEmail = jdbcTemplate.query(sql,(resultSet,rowNum)->
            resultSet.getString("email")
            ,email);

        return DuplicatedEmail.isEmpty();
    }
}
