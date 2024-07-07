package gift.wish.repository;

import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.Password;
import gift.product.domain.Product;
import gift.wish.domain.Wish;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class WishRepository {
    private final JdbcClient jdbcClient;

    public WishRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Wish> findAll(Long memberId) {
        String sql = "select * from wishes where member_id = ?";
        return jdbcClient.sql(sql)
                .param(memberId)
                .query(Wish.class)
                .list();
    }



    public void save(Wish wish) {
        Assert.notNull(wish, "Wish must not be null");
        if (wish.checkNew()) {
            String sql = "INSERT INTO wishes (member_id, product_id, product_count) VALUES (?, ?, ?)";
            jdbcClient.sql(sql)
                    .param(wish.getMemberId())
                    .param(wish.getProductId())
                    .param(wish.getProductCount().getValue())
                    .update();

        }
        if (!wish.checkNew()) {
            String sql = "UPDATE wishes SET member_id = ?, product_id = ?, product_count = ? WHERE id = ?";
            jdbcClient.sql(sql)
                    .param(wish.getMemberId())
                    .param(wish.getProductId())
                    .param(wish.getProductCount().getValue())
                    .param(wish.getId())
                    .update();
        }
    }

    public Optional<Wish> findById(Long id) {
        String sql = "select * from wishes where id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(Wish.class)
                .optional();
    }

    public void deleteById(Long id) {
        String sql = "delete from wishes where id = ?";
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
}
