package gift.Login.repository;

import gift.Login.model.Product;
import gift.Login.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getLong("price"));
            product.setTemperatureOption(rs.getString("temperatureOption"));
            product.setCupOption(rs.getString("cupOption"));
            product.setSizeOption(rs.getString("sizeOption"));
            product.setImageUrl(rs.getString("imageurl"));
            return product;
        }
    }

    public Wishlist findWishlistByMemberId(UUID memberId) {
        String sql = "SELECT p.* FROM product p INNER JOIN wishlist w ON p.id = w.product_id WHERE w.member_id = ?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{memberId}, new ProductRowMapper());
        return new Wishlist(memberId, products);
    }

    public void addProductToWishlist(UUID memberId, Product product) {
        // product 테이블에 먼저 추가
        String productSql = "INSERT INTO product (name, price, temperatureOption, cupOption, sizeOption, imageurl) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(productSql, new String[] {"id"});
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            ps.setString(3, product.getTemperatureOption());
            ps.setString(4, product.getCupOption());
            ps.setString(5, product.getSizeOption());
            ps.setString(6, product.getImageUrl());
            return ps;
        }, keyHolder);

        // 생성된 product ID 가져오기
        Long generatedProductId = keyHolder.getKey().longValue();
        System.out.println("generatedProductId = " + generatedProductId);

        // wishlist 테이블에 추가
        String wishlistSql = "INSERT INTO wishlist (product_id, member_id) VALUES (?, ?)";
        jdbcTemplate.update(wishlistSql, generatedProductId, memberId);
    }

    public void updateProductInWishlist(UUID memberId, Long productId, Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, temperatureOption = ?, cupOption = ?, sizeOption = ?, imageurl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getTemperatureOption(), product.getCupOption(), product.getSizeOption(), product.getImageUrl(), productId);
    }

    public void removeProductFromWishlist(UUID memberId, Long productId) {
        String sql = "DELETE FROM wishlist WHERE product_id = ? AND member_id = ?";
        jdbcTemplate.update(sql, productId, memberId);
    }

    public Product findProductByIdAndMemberId(Long productId, UUID memberId) {
        String sql = "SELECT p.* FROM product p INNER JOIN wishlist w ON p.id = w.product_id WHERE w.product_id = ? AND w.member_id = ?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{productId, memberId}, new ProductRowMapper());
        return products.isEmpty() ? null : products.get(0);
    }
}
