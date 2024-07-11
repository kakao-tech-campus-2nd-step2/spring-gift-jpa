package gift.domain.cart.repository;

import gift.domain.cart.CartItem;
import gift.global.exception.BusinessException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateCartItemRepository implements CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 장바구니에 상품 ID 추가
     */
    public void addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO cart (user_id, product_id) VALUES (?, ?)";

        int rowNum = jdbcTemplate.update(sql, cartItem.getId(), cartItem.getProductId());

        if (rowNum != 1) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "장바구니 담기에 실패했습니다.");
        }
    }


    /**
     * 장바구니 조회
     */
    public List<CartItem> getCartItemsByUserId(Long userId) {
        String sql = "SELECT * FROM cart WHERE user_id = ?";

        List<CartItem> cartItems = jdbcTemplate.query(sql,
            BeanPropertyRowMapper.newInstance(CartItem.class), userId);

        return cartItems;
    }


    /**
     * 장바구니 상품 존재 여부 확인
     */
    public boolean isExistsInCart(Long userId, Long productId) {
        String sql = "SELECT CASE WHEN EXISTS ("
                     + "    SELECT 1 "
                     + "    FROM cart "
                     + "    WHERE user_id = ? AND product_id = ?"
                     + ") THEN TRUE ELSE FALSE END AS product_exists";

        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, productId);
        return result;
    }

    /**
     * 장바구니 상품 삭제
     */
    public void deleteCartItem(Long userId, Long productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";

        int rowNum = jdbcTemplate.update(sql, userId, productId);

        if (rowNum == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "삭제할 상품이 장바구니에 존재하지 않습니다.");
        }
    }
}
