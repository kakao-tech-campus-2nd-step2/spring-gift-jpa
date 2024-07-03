package gift.Repository;

import gift.DTO.productDto;
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

  public void insertProduct(productDto productDTO) {
    var sql = "insert into product(name, price, imageUrl) values (?, ?, ?)";
    jdbcTemplate.update(sql, productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl());
  }

  public productDto selectProduct(long id) {
    var sql = "select * from product where id=?";
    return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
  }

  public List<productDto> selectAllProducts() {
    var sql = "SELECT * FROM product";
    return jdbcTemplate.query(sql, productRowMapper());
  }

  public void deleteProduct(long id) {
    var sql = "DELETE FROM product WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  public void updateProduct(productDto productDTO) {
    var sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
    jdbcTemplate.update(sql, productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl(), productDTO.getId());
  }

  private RowMapper<productDto> productRowMapper() {
    return (resultSet, rowNum) -> new productDto(
      resultSet.getLong("id"),
      resultSet.getString("name"),
      resultSet.getInt("price"),
      resultSet.getString("imageUrl")
    );
  }
}
