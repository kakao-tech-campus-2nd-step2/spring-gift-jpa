package gift.dao;

import gift.model.wish.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishDao {
    private final JdbcTemplate jdbcTemplate;
    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> getAllWishes() {
        var sql = "select productid, productName, amount from wishes";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Wish(
                        resultSet.getLong("productid"),
                        resultSet.getString("productName"),
                        resultSet.getInt("amount")
                )
        );
    }

    public void insertWish(Wish wish) {
        var sql = "insert into wishes (productid, productName, amount) values (?, ?, ?)";
        jdbcTemplate.update(sql, wish.getProductId(), wish.getProductName(), wish.getAmount());
    }

    public void deleteWish(long id) {
        var sql = "delete from wishes where productid = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateWish(Wish wish){
        var sql = "update products set amount = ? where id = ? ";
        jdbcTemplate.update(sql, wish.getAmount(), wish.getProductId());
    }
}
