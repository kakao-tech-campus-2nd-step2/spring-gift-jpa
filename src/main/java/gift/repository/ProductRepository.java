package gift.repository;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Product> rowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getLong("price"));
        product.setTemperatureOption(rs.getString("temperatureOption"));
        product.setCupOption(rs.getString("cupOption"));
        product.setSizeOption(rs.getString("sizeOption"));
        product.setImageurl(rs.getString("imageurl"));
        return product;
    };

    public List<Product> findAll() {
        var sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Product> findById(Long id) {
        var sql = "SELECT * FROM product WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Product save(Product product) {
        var sql = "INSERT INTO product (name, price, temperatureOption, cupOption, sizeOption, imageurl) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getTemperatureOption(), product.getCupOption(), product.getSizeOption(), product.getImageurl());
        return product;
    }

    public Product update(Product product) {
        var sql = "UPDATE product SET name = ?, price = ?, temperatureOption = ?, cupOption = ?, sizeOption = ?, imageurl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getTemperatureOption(), product.getCupOption(), product.getSizeOption(), product.getImageurl(), product.getId());
        return product;
    }

    @Transactional
    public void deleteById(Long id) {
        var sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
