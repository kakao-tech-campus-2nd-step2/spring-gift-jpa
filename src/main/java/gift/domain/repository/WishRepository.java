package gift.domain.repository;

import gift.domain.dto.WishResponseDto;
import gift.domain.entity.User;
import gift.domain.entity.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Wish> getWishlistRowMapper() {
        return (resultSet, rowNum) -> new Wish(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("user_id"),
            resultSet.getLong("quantity")
        );
    }

    private RowMapper<WishResponseDto> getWishlistResponseDtoRowMapper() {
        return (resultSet, rowNum) -> new WishResponseDto(
            resultSet.getLong("product_id"),
            resultSet.getString("name"),
            resultSet.getLong("price"),
            resultSet.getString("image_url"),
            resultSet.getLong("quantity")
        );
    }

    public Optional<Wish> findById(Long id) {
        try {
            String sql = "SELECT * from wishes WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getWishlistRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Wish> findByUserEmailAndProductId(Long userId, Long productId) {
        try {
            String sql = "SELECT * from wishes WHERE product_id = ? AND user_id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getWishlistRowMapper(), productId, userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<WishResponseDto> findWishlistByUser(User user) {
        String sql = "SELECT product_id, name, price, image_url, quantity "
            + "FROM wishes INNER JOIN products ON products.id = wishes.product_id "
            + "WHERE user_id = ?";
        return jdbcTemplate.query(sql, getWishlistResponseDtoRowMapper(), user.id());
    }

    public void save(Wish wish) {
        String sqlInsert = "INSERT INTO wishes (product_id, user_id, quantity) values (?, ?, ?)";
        jdbcTemplate.update(sqlInsert, wish.productId(), wish.userId(), wish.quantity());
    }

    public void update(Wish wish) {
        String sql = "UPDATE wishes SET quantity = ? WHERE product_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, wish.quantity(), wish.productId(), wish.userId());
    }

    public void delete(Wish wish) {
        String sqlDelete = "DELETE FROM wishes WHERE product_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlDelete, wish.productId(), wish.userId());
    }
}
