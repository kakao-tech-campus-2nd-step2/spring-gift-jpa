package gift.repository;

import gift.converter.StringToUrlConverter;
import gift.domain.Product;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Product product) {
        String sql = "insert into product (name, price, image_url) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getImageUrl().toString());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select id, name, price, image_url from product where id = ?",
            getProductRowMapper(), id));
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("select id, name, price, image_url from product",
            getProductRowMapper());
    }

    public boolean deleteById(Long id) {
        if (existsById(id)) {
            jdbcTemplate.update("delete from product where id = ?", id);
            return true;
        }
        return false;
    }

    public int update(Long id, Product product) {
        String sql = "update product set name = ?, price = ?, image_url = ? where id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImageUrl().toString(), id);
    }

    public boolean existsById(Long id) {
        String sql = "select count(*) from product where id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private RowMapper<Product> getProductRowMapper() {
        return (rs, rowNum) -> new Product.Builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .price(rs.getInt("price"))
            .imageUrl(StringToUrlConverter.convert(rs.getString("image_url")))
            .build();
    }

}
