package gift.repository;

import gift.domain.WishProduct;
import gift.repository.mapper.WishProductRowMapper;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WishProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(WishProduct wishProduct) {
        String sql = "INSERT INTO wish_product (member_id, product_id, quantity) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"wish_product_id"});
            ps.setLong(1, wishProduct.getMember().getId());
            ps.setLong(2, wishProduct.getProduct().getId());
            ps.setInt(3, wishProduct.getQuantity());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<WishProduct> findById(Long id) {
        String sql =
            "SELECT wp.wish_product_id, wp.quantity, " +
            "       m.member_id, m.name AS member_name, " +
            "       p.product_id, p.name AS product_name, p.price, p.image_url " +
            "FROM wish_product wp " +
            "JOIN member m ON wp.member_id = m.member_id " +
            "JOIN product p ON wp.product_id = p.product_id " +
            "WHERE wp.wish_product_id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                getWishProductRowMapper(), id
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<WishProduct> findByMemberId(Long memberId) {
        String sql =
            "SELECT wp.wish_product_id, wp.quantity, " +
            "       m.member_id, m.name AS member_name, " +
            "       p.product_id, p.name AS product_name, p.price, p.image_url " +
            "FROM wish_product wp " +
            "JOIN member m ON wp.member_id = m.member_id " +
            "JOIN product p ON wp.product_id = p.product_id " +
            "WHERE wp.member_id = ?";

        return jdbcTemplate.query(sql,
            getWishProductRowMapper(), memberId
        );
    }

    public Optional<WishProduct> findByMemberIdAndProductId(Long MemberId, Long productId) {
        String sql =
            "SELECT wp.wish_product_id, wp.quantity, " +
            "       m.member_id, m.name AS member_name, " +
            "       p.product_id, p.name AS product_name, p.price, p.image_url " +
            "FROM wish_product wp " +
            "JOIN member m ON wp.member_id = m.member_id " +
            "JOIN product p ON wp.product_id = p.product_id " +
            "WHERE wp.member_id = ? AND wp.product_id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                getWishProductRowMapper(), MemberId, productId
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int updateQuantity(WishProduct wishProduct) {
        String sql = "UPDATE wish_product SET quantity = ? WHERE wish_product_id = ?";
        return jdbcTemplate.update(sql, wishProduct.getQuantity(), wishProduct.getId());
    }

    public boolean deleteById(Long id) {
        if (existsById(id)) {
            jdbcTemplate.update("DELETE FROM wish_product WHERE wish_product_id = ?", id);
            return true;
        }
        return false;
    }

    private boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM wish_product WHERE wish_product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private RowMapper<WishProduct> getWishProductRowMapper() {
        return new WishProductRowMapper();
    }
}
