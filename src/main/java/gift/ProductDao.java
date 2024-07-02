package gift;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

  private final JdbcTemplate jdbcTemplate;

  public ProductDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void dropProductTable() {
    var sql = "DROP TABLE product";
    jdbcTemplate.execute(sql);
  }

  public void insertProduct(Product product) {
    var sql = "insert into product(name, price, imageUrl) values (?, ?, ?)";
    jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
  }

  public Product selectProduct(long id) {
    var sql = "select * from product where id=?";
    return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
  }

  public List<Product> selectAllProducts() {
    var sql = "SELECT * FROM product";
    return jdbcTemplate.query(sql, productRowMapper());
  }

  public void deleteProduct(long id) {
    var sql = "DELETE FROM product WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  public void updateProduct(Product product) {
    var sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
    jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
  }

  private RowMapper<Product> productRowMapper() {
    return (resultSet, rowNum) -> new Product(
      resultSet.getLong("id"),
      resultSet.getString("name"),
      resultSet.getInt("price"),
      resultSet.getString("imageUrl")
    );
  }
}
