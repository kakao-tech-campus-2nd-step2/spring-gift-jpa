package gift.model.wish;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class WishRowMapper implements RowMapper<Wish> {

    @Override
    public Wish mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Wish(
            resultSet.getLong("id"),
            resultSet.getString("user_id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("count")
        );
    }
}
