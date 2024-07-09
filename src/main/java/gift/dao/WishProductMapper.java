package gift.dao;

import gift.vo.WishProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishProductMapper implements RowMapper<WishProduct> {

    @Override
    public WishProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WishProduct(
                rs.getLong("wish_product_id"),
                rs.getString("member_email"),
                rs.getLong("product_id")
        );
    }
}
