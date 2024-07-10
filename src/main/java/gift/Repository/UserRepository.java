package gift.Repository;

import gift.Exception.ForbiddenException;
import gift.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbctemplate;


    public UserRepository(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    public void insertUser(String email, String password){
        var sql = "insert into Users (email, password) values (?,?)";
        jdbctemplate.update(sql, email, password);
    }

    public String login(String email, String password) {
        var sql = "select password from Users where email = ? AND password = ?";
        try{
            return jdbctemplate.queryForObject(sql, new Object[]{email, password}, String.class);
        } catch (EmptyResultDataAccessException e){
            throw new ForbiddenException("잘못된 로그인입니다");
        }
    }

    public User findByEmail(String email){
        var sql = "select * from Users where email = ?";
        try {
            return jdbctemplate.queryForObject(sql, new Object[]{email}, userRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ForbiddenException("매칭되는 유저가 없습니다");
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

}
