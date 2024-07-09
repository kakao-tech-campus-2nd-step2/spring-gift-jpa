package gift.repository;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private RowMapper<Product> productRowMapper = (rs, rowNum) -> {
    Product product = new Product();
    product.setId(rs.getLong("id"));
    product.setName(rs.getString("name"));
    product.setPrice(rs.getInt("price"));
    product.setImageUrl(rs.getString("image_url"));
    return product;
  };

  @Override
  public List<Product> findAll() {
    String sql = "SELECT * FROM product";
    return jdbcTemplate.query(sql, productRowMapper);
  }

  @Override
  public Optional<Product> findById(Long id) {
    String sql = "SELECT * FROM product WHERE id = ?";
    List<Product> products = jdbcTemplate.query(sql, productRowMapper, id);
    return products.stream().findFirst();
  }

  @Override
  public int save(Product product) {
    String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
    return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
  }

  @Override
  public int update(Product product) {
    String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
    return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
  }

  @Override
  public int deleteById(Long id) {
    String sql = "DELETE FROM product WHERE id = ?";
    return jdbcTemplate.update(sql, id);
  }
}


