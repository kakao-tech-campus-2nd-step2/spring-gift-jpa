package gift.wishlist.repository;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class WishListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; // jdbc 로 db 연결

    // userId로 위시리스트를 조회
    public WishList findByUserId(Long userId) {
        String sql = "SELECT * FROM wish_list WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new WishListMapper());
    }

    // 위시리스트에 제품을 추가하는 메서드
    public void addProductToWishList(Long userId, Product product) {
        String sql = "INSERT INTO product (name, price, imgUrl, wishlist_id) VALUES (?, ?, ?, (SELECT id FROM wish_list WHERE user_id = ?))";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imgUrl(), userId);
    }

    // 위시리스트에서 제품을 제거하는 메서드
    public void removeProductFromWishList(Long userId, Long productId) {
        String sql = "DELETE FROM product WHERE id = ? AND wishlist_id = (SELECT id FROM wish_list WHERE user_id = ?)";
        jdbcTemplate.update(sql, productId, userId);
    }

    // 위시리스트 객체를 매핑하는 내부 클래스
    private static final class WishListMapper implements RowMapper<WishList> {
        @Override
        public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            Long userId = rs.getLong("user_id");
            String name = rs.getString("name");

            WishList wishList = new WishList(id, userId, name);
            return wishList;
        }
    }
}