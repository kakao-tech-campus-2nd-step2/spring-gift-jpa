package gift.repository.mapper;

import gift.converter.StringToUrlConverter;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.WishProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class WishProductRowMapper implements RowMapper<WishProduct> {

    /**
     * 매핑 시, 컬럼 이름이 지정 되어 있으므로 양식에 맞추어 사용하여야 한다.
     * @param rs the {@code ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return WishProduct
     * @throws SQLException
     */
    @Override
    public WishProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member.Builder()
            .id(rs.getLong("member_id"))
            .name(rs.getString("member_name"))
            .build();

        Product product = new Product.Builder()
            .id(rs.getLong("product_id"))
            .name(rs.getString("product_name"))
            .price(rs.getInt("price"))
            .imageUrl(StringToUrlConverter.convert(rs.getString("image_url")))
            .build();

        return new WishProduct.Builder()
            .id(rs.getLong("wish_product_id"))
            .member(member)
            .product(product)
            .quantity(rs.getInt("quantity"))
            .build();
    }
}