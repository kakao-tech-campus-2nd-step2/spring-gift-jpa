package gift.repository;

import gift.common.exception.ProductNotFoundException;
import gift.common.exception.UserNotFoundException;
import gift.enums.ReadQuery;
import gift.enums.WriteQuery;
import gift.model.product.Product;
import gift.model.user.User;
import gift.model.user.UserRequest;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User save(UserRequest userRequest) {
        var sql = "INSERT INTO users (password, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, userRequest.password(), userRequest.email());

        User user = findByEmail(userRequest.email());
        return user;
    }

    public User findByEmail(String email) {
        var sql = ReadQuery.FIND_USER_BY_EMAIL.getQuery();
        try {
            return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("password"),
                    resultSet.getString("email")
                ),
                email
            );
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    public boolean existsByEmail(String email) {
        var sql = ReadQuery.COUNT_USER_BY_EMAIL.getQuery();
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count > 0;
    }

    public List<Product> findWishList(Long id) {
        var sql = ReadQuery.FIND_ALL_WISH.getQuery();
        try {
            return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("imageUrl")
                ),
                id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException();
        }
    }

    public void registerWishList(Long userId, Long productId, int count) {
        var sql = WriteQuery.REGISTER_WISH.getQuery();

        for (int i = 0; i < count; i++) {
            jdbcTemplate.update(sql, userId, productId);
        }
    }

    public void delete(Long userId, Long productId, int count) {
        var sql = WriteQuery.DELETE_WISH.getQuery();
        jdbcTemplate.update(sql, userId, productId, count);
    }
}
