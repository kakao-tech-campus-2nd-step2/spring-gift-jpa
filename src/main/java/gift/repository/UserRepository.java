package gift.repository;

import gift.domain.Product;
import gift.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     * User 정보를 받아 DB에 저장
     */
    public void save(User user){
        var sql = "INSERT INTO users(userId, email, password) VALUES (?,?,?)";
        jdbcTemplate.update(sql,user.getUserId(), user.getEmail(), user.getPassword());
    }
    /*
     * DB에 저장된 모든 User 정보를 가져와 반환
     */
    public List<User> findAll(){
        String sql = "select userId, email, password from users";
        List<User> users = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    User user = new User(
                            resultSet.getString("userId"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                    return user;
                });
        return users;
    }
    /*
     * User 정보를 email을 기준으로 DB에서 찾아와 반환
     */
    public User findByUserId(String userId) {
        String sql = "select userId, email, password, from users where userId = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{userId}, (resultSet, rowNum) -> {
                    User userEntity = new User(
                            resultSet.getString("userId"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                    return userEntity;
                });
        return user;
    }
}
