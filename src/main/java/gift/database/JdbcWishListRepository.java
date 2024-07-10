package gift.database;

import gift.model.WishList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcWishListRepository {

    private final JdbcTemplate template;

    public JdbcWishListRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        createTable();
    }

    private void createTable() {
        template.update("create table if not exists wishlist("
            + "id long primary key auto_increment, "
            + "member_id long not null,"
            + "product_id long not null,"
            + "product_value int not null)");
    }

    public WishList findByMemeberId(Long memberId) {
        String sql = "select * from wishlist where member_id = ?";
        return template.queryForObject(sql, wishListRowMapper());
    }

    public void insertWishList(WishList wishList) {
        String sql = "insert into wishlist (member_id, product_id, product_value) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        List<Long> productIds = wishList.getWishList().keySet().stream().toList();

        template.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setLong(1, wishList.getMemberId());
                ps.setLong(2, productIds.get(i));
                ps.setInt(3, wishList.getWishList().get(productIds.get(i)));
            }

            @Override
            public int getBatchSize() {
                return 1;
            }
        });
    }

    public void updateWishList(WishList wishList) {
        String sql = "update wishlist set member_id = ?, product_id = ?,"
            + " product_value = ? where member_id =?,product_id=?";
        for (long productId : wishList.getWishList().keySet()) {
            template.update(sql, wishList.getMemberId(), productId,
                wishList.getWishList().get(productId));
        }
    }

    public void deleteWishList(long memberId, long productId) {
        String sql = "delete from wishlist where member_id = ? and product_id = ?";
        template.update(sql, memberId, productId);
    }

    private RowMapper<WishList> wishListRowMapper() {
        return (rs, rowNum) -> {
            WishList wishList = new WishList(
                rs.getLong("id"),
                rs.getLong("member_id"),
                new HashMap<>()
            );
            wishList.updateProduct(rs.getLong("product_id"), rs.getInt("product_value"));
            return wishList;
        };
    }
}
