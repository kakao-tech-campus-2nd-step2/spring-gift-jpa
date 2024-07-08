package gift.repository;

import gift.domain.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private final JdbcClient jdbcClient;

    public WishRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long insertWish(Wish wish){
        String sql = "insert into wish (userId, productId, count) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
            .params(wish.getUserId(), wish.getProductId(), wish.getCount())
            .update(keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Wish> selectWish(Long wishId){
        String sql = "select id, userId, productId, count from wish where id = ?";
        return jdbcClient.sql(sql)
            .param(wishId)
            .query(Wish.class)
            .optional();
    }

    public List<Wish> selectAllWish(Long userId){
        String sql = "select id, userId, productId, count from wish where userId = ?";
        return jdbcClient.sql(sql)
            .param(userId)
            .query(Wish.class)
            .list();
    }

    public void updateWish(Wish wish){
        String sql = "update wish set count = ? where id = ?";
        jdbcClient.sql(sql)
            .params(wish.getCount(), wish.getId())
            .update();
    }

    public void deleteWish(Long wishId){
        String sql = "delete from wish where id = ?";
        jdbcClient.sql(sql)
            .param(wishId)
            .update();
    }
}
