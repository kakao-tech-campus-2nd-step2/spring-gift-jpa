package gift.repository.mapper;

import gift.converter.StringToUrlConverter;
import gift.domain.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product.Builder()
            .id(rs.getLong("product_id"))
            .name(rs.getString("name"))
            .price(rs.getInt("price"))
            .imageUrl(StringToUrlConverter.convert(rs.getString("image_url")))
            .build();
    }
}
