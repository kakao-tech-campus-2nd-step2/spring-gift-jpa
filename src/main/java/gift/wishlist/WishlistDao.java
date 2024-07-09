package gift.wishlist;

import gift.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class WishlistDao {

    private JdbcClient jdbcClient;
    private JdbcTemplate jdbcTemplate;

    public WishlistDao(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWish(Member member, Long productId) {
        String sql = """
            INSERT INTO wishlist (member_id, product_id) 
            VALUES (?,?)
            """;
//        jdbcClient.sql(sql)
//            .param(member.getId())
//            .param(productId)
//            .update();
        jdbcTemplate.update(sql,member.getId(), productId);
    }

    public List<Long> findAllWish() {
        String sql = """
            SELECT product_id
            FROM wishlist
            """;

        RowMapper<Long> rowMapper = (rs, rowNum) -> rs.getLong("product_id");

        return jdbcTemplate.query(sql, rowMapper);

        //return  jdbcClient.sql(sql).query(Long.class).list();

    }

    public Optional<Long> findProductById(Long product_id) {
        String sql = """
            SELECT product_id 
            FROM wishlist 
            WHERE product_id = ?
            """;

        Long productId = jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> rs.getLong("product_id"),
            product_id
        );

        return Optional.ofNullable(productId);

        //return jdbcClient.sql(sql).param(product_id).query(Long.class).optional();
    }

    public void deleteWish(Long productId) {
        String sql = """
            DELETE FROM wishlist 
            WHERE product_id = ?
            """;
//        jdbcClient.sql(sql)
//            .param(productId)
//            .update();
        jdbcTemplate.update(sql,productId);
    }
}

