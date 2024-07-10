package gift.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import gift.config.DatabaseProperties;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.WishList;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final DatabaseProperties databaseProperties;
    private final Validator validator;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate, DatabaseProperties databaseProperties, Validator validator) {
        this.jdbcTemplate = jdbcTemplate;
        this.databaseProperties = databaseProperties;
        this.validator = validator;
    }

    @Override
    public void run(String... args) throws Exception {
        var sqlDropTable = "DROP TABLE IF EXISTS userDB";

        var sqlCreateTable = """
                CREATE TABLE userDB (
                    email VARCHAR(255) PRIMARY KEY,
                    password VARCHAR(50) NOT NULL,
                    name VARCHAR(50) NOT NULL,
                    role VARCHAR(50) NOT NULL,
                    wishList VARCHAR(200000)
                );
                """;

        jdbcTemplate.execute(sqlDropTable);
        jdbcTemplate.execute(sqlCreateTable);
    }

    public void insertUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        var sql = "INSERT INTO userDB(email, password, name, role) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(),
                user.getRole());
    }

    public Integer countUser(User user) {
        var sql = "SELECT COUNT(*) FROM userDB WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, user.getEmail(), user.getPassword());
    }

    public Integer countUser(String email) {
        var sql = "SELECT COUNT(*) FROM userDB WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }

    public User getUser(String email) {
        var sql = "SELECT * FROM userDB WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    public Map<String, Object> getWishLists(String email) {
        var sql = "SELECT wishList FROM userDB WHERE email = ?";
        User user = getUser(email);
        String wishListJson = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> rs.getString("wishList"), email);
        if (wishListJson != null && !wishListJson.isBlank()) {
            Gson gson = new Gson();
            WishList wishList = gson.fromJson(wishListJson, WishList.class);
            Map<String, Object> wishListMap = new HashMap<>();
            wishListMap.put("wishList", wishList);
            wishListMap.put("user", user);
            return wishListMap;
        }
        return null;
    }

    public Integer saveWishList(User user, WishList wishList) throws JsonProcessingException {
        var sql = "UPDATE userDB SET wishList = ? WHERE email = ?";
        Gson gson = new Gson();
        return jdbcTemplate.update(sql, gson.toJson(wishList), user.getEmail());
    }
}
