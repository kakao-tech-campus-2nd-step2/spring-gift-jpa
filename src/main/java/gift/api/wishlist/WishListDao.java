package gift.api.wishlist;

import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {

    private final JdbcClient jdbcClient;

    public WishListDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<WishList> getAllWishes(Long id) {
        return jdbcClient.sql("select * from wishlist where memberId = :memberId")
                        .param("memberId", id, Types.BIGINT)
                        .query(WishList.class)
                        .list();
    }

    public void insert(WishListRequest wishListRequest, Long id) {
        jdbcClient.sql("insert into wishlist (memberId, productId, quantity) values (:memberId, :productId, :quantity)")
            .param("memberId", id, Types.BIGINT)
            .param("productId", wishListRequest.productId(), Types.BIGINT)
            .param("quantity", wishListRequest.quantity(), Types.INTEGER)
            .update();
    }

    public void update(WishListRequest wishListRequest, Long id) {
        jdbcClient.sql("update wishlist set quantity = :quantity where memberId = :memberId and productId = :productId")
            .param("quantity", wishListRequest.quantity(), Types.INTEGER)
            .param("memberId", id, Types.BIGINT)
            .param("productId", wishListRequest.productId(), Types.BIGINT)
            .update();
    }

    public void delete(WishListRequest wishListRequest, Long id) {
        jdbcClient.sql("delete from wishlist where memberId = :memberId and productId = :productId")
            .param("memberId", id, Types.BIGINT)
            .param("productId", wishListRequest.productId(), Types.BIGINT)
            .update();
    }
}
