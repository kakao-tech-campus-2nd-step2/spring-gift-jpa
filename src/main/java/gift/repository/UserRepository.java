package gift.repository;


import gift.entity.Product;
import gift.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isExistAccount(String email) {
        String sql = "select count(*) from user_tb where email=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count > 0;
    }

    public void saveUser(User user) {

        String sql = "insert into user_tb(id, email, password) values(?,?,?)";
        String hashPw = passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update(sql,user.getId(),user.getEmail(),hashPw);
    }

    public User findUserbyID(String email){
        String sql= "select * from user_tb where email=?";
        List<User> user = jdbcTemplate.query(sql,new Object[]{email},(rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password")
        ));
        return user.getFirst();

    }
}
