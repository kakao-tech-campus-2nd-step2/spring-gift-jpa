package gift.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductWithQuantityDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductWithQuantityDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void saveProductWithQuantity(long productId, int quantity,long wishlistId){
        String sql="INSERT INTO ProductWithQuantity(product_id,quantity,wishlist_id) values(?,?,?)";
        jdbcTemplate.update(sql,productId,quantity,wishlistId);
    }

}
