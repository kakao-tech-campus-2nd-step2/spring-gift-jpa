package gift.Repository;

import gift.Model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);

    public int add(Product product){
        return jdbcTemplate.update("INSERT INTO product (name, price, imageUrl) VALUES (?,?,?)",product.getName(),product.getPrice(),product.getImageUrl());
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", rowMapper);
    }

    public Product findById(Long id){
        return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?", rowMapper, id);
    }

    public int update(Product product){
        return jdbcTemplate.update("UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?", product.getName(), product.getPrice(), product.getImageUrl(),product.getId());
    }

    public int delete(Long id){
        return jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
