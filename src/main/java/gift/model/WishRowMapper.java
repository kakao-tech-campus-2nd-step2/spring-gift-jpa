package gift.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishRowMapper implements RowMapper<Wish> {
    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product(
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
        return new Wish(
                rs.getLong("id"),
                new Member(rs.getLong("member_id"), null, null, null, null, null),
                rs.getInt("product_count"),
                product,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
