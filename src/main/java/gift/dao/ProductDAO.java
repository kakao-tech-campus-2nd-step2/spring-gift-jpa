package gift.dao;

import gift.entity.Product;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //sql 쿼리 결과를 자바 객체에 매핑시켜주는 도구
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", rowMapper);
    }

    public Product findById(Long id) {
        List<Product> products = jdbcTemplate.query("SELECT * FROM product where id = ?", rowMapper,
            id);
        if (products.isEmpty()) {
            return null;
        }
        return jdbcTemplate.queryForObject("SELECT * FROM product where id = ?", rowMapper, id);
    }

    public int save(Product product) {
        return jdbcTemplate.update("INSERT INTO product (name, price, img) VALUES (?, ?, ?)",
            product.getName(), product.getPrice(), product.getImg());
    }

    public int update(Product product) {
        return jdbcTemplate.update("UPDATE product SET name = ?, price = ?, img = ? WHERE id = ?",
            product.getName(), product.getPrice(), product.getImg(), product.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
