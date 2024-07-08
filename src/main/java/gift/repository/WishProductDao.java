package gift.repository;

import gift.exception.WishListException;
import gift.model.Product;
import gift.model.WishProduct;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishProductDao {

    private final JdbcTemplate jdbcTemplate;

    public WishProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll(Long memberId) {
        var sql = "SELECT * FROM products WHERE id"
            + " in (SELECT product_id FROM wish_products WHERE member_id = ?)";
        return jdbcTemplate.query(sql, productRowMapper, memberId);
    }

    public void insert(WishProduct wishProduct) {
        try {
            var sql = "INSERT INTO wish_products(member_id, product_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, wishProduct.getMemberId(), wishProduct.getProductId());
        } catch (DuplicateKeyException e) {
            throw new WishListException("해당 제품이 이미 위시 리스트에 존재합니다.");
        }

    }

    public void delete(WishProduct wishProduct) {

        var sql = "DELETE FROM wish_products WHERE member_id = ? AND product_id = ?";
        int updated = jdbcTemplate.update(sql, wishProduct.getMemberId(),
            wishProduct.getProductId());
        if (updated == 0) {
            throw new WishListException("위시 리스트에 해당 제품이 존재하지 않습니다.");
        }

    }



    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getString("image_url")
    );

}
