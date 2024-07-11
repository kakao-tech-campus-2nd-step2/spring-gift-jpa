package gift.repository;

import gift.exception.wishlist.DuplicateWishException;
import gift.model.Product;
import gift.model.Wish;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishDao {

    private final JdbcTemplate jdbcTemplate;

    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll(Long memberId) {
        var sql = "SELECT * FROM products WHERE id"
            + " in (SELECT product_id FROM wish_products WHERE member_id = ?)";
        return jdbcTemplate.query(sql, productRowMapper, memberId);
    }

    public void insert(Wish wish) {
        try {
            var sql = "INSERT INTO wish_products(member_id, product_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, wish.getMember().getId(), wish.getProduct().getId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateWishException("해당 제품이 이미 위시 리스트에 존재합니다.");
        }

    }

    public void delete(Wish wish) {

        var sql = "DELETE FROM wish_products WHERE member_id = ? AND product_id = ?";
        int updated = jdbcTemplate.update(sql, wish.getMember().getId(),
            wish.getProduct().getId());
        if (updated == 0) {
            throw new DuplicateWishException("위시 리스트에 해당 제품이 존재하지 않습니다.");
        }

    }



    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getString("image_url")
    );

}
