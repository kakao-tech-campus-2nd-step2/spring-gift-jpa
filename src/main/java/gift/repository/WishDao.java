package gift.repository;

import gift.controller.dto.request.WishInsertRequest;
import gift.controller.dto.request.WishPatchRequest;
import gift.model.Wish;
import gift.model.WishRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishDao {
    private final JdbcClient jdbcClient;


    public WishDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void save(WishInsertRequest request, Long memberId) {
        var sql = "insert into wish(product_id, product_count, member_id, created_by, updated_by) values(?, ?, ?, ?, ?)";
        jdbcClient.sql(sql)
                .params(request.productId(), 1, memberId, memberId, memberId)
                .update();
    }

    public void update(WishPatchRequest request, Long memberId) {
        var sql = "update wish set product_count = ?, updated_by = ? where member_id = ? and product_id = ?";
        jdbcClient.sql(sql)
                .params(request.productCount(), memberId, memberId, request.productId())
                .update();
    }

    public List<Wish> findAllByMemberId(Long memberId) {
        var sql = "select * from wish w left join product p " +
                "on w.product_id = p.id where w.member_id = ? " +
                "order by w.created_at";
        return jdbcClient.sql(sql)
                .params(memberId)
                .query(new WishRowMapper())
                .list();
    }

    public void deleteByProductId(Long productId, Long memberId) {
        var sql = "delete from wish where product_id = ? and member_id = ?";
        jdbcClient.sql(sql)
                .params(productId, memberId)
                .update();
    }

    public boolean existsByProductIdAndMemberId(Long productId, Long memberId) {
        var sql = "select count(*) from wish where product_id = ? and member_id = ?";
        int count = jdbcClient.sql(sql)
                .params(productId, memberId)
                .query(Integer.class)
                .single();
        return count > 0;
    }
}
