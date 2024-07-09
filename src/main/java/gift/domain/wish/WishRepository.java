package gift.domain.wish;

import gift.dto.WishAddRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addWish(Long memberId, WishAddRequestDto request) {
        int currentQuantity = getCurrentQuantity(memberId, request.getProductId());
        if (currentQuantity > 0){
            String sql = "UPDATE wish SET quantity = ? where member_id = ? and product_id = ?";
            jdbcTemplate.update(sql,currentQuantity+request.getQuantity(),memberId,request.getProductId());
            return;
        }
        String sql = "INSERT INTO wish(member_id,product_id,quantity) VALUES(?,?,?)";
        jdbcTemplate.update(sql, memberId, request.getProductId(), request.getQuantity());
    }

    public List<Wish> getAllWishes(Long memberId) {
        String sql = "SELECT * FROM wish WHERE member_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNow) -> {
            Long id = rs.getLong("id");
            Long productId = rs.getLong("product_id");
            int quantity = rs.getInt("quantity");
            return new Wish(id, memberId, productId, quantity);
        }, memberId);
    }

    public void deleteWish(Long memberId, Long productId) {
        String sql = "DELETE FROM wish WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql,memberId,productId);
    }

    private int getCurrentQuantity(Long memberId,Long productId) {
        String sql = "SELECT quantity FROM wish WHERE member_id = ? AND product_id = ?";
        List<Integer> quantities = jdbcTemplate.query(sql, new Object[]{memberId, productId},
                (rs, rowNum) -> rs.getInt("quantity"));

        if (quantities.isEmpty()) {
            return 0;
        } else {
            return quantities.getFirst();
        }
    }
}
