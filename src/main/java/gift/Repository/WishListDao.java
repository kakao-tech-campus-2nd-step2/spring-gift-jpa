package gift.Repository;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import gift.DTO.WishListDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {

  JdbcTemplate jdbcTemplate;

  public WishListDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insertWishList(ProductDto wishProduct) {
    var sql = """
      INSERT INTO wishList(name,price,imageUrl) VALUES(?, ?, ?);
      """;
    jdbcTemplate.update(sql, wishProduct.getName(), wishProduct.getPrice(),
      wishProduct.getImageUrl());
  }


  public WishListDto selectWishList(UserDto user) {
    var sql = """
      SELECT * FROM wishList;
      """;
    return new WishListDto(jdbcTemplate.query(sql, wishListRowMapper()));
  }

  public ProductDto selectWishProduct(Long id) {
    var sql = """
      SELECT * FROM wishList where id =?;
      """;
    return jdbcTemplate.queryForObject(sql, wishListRowMapper(), id);
  }

  private RowMapper<ProductDto> wishListRowMapper() {
    return (rs, rowNum) -> new ProductDto(
      rs.getLong("id"),
      rs.getString("name"),
      rs.getInt("price"),
      rs.getString("imageUrl")
    );
  }

  public void deleteWishProduct(Long id) {
    var sql = """
      DELETE FROM wishList WHERE id =?
      """;
    jdbcTemplate.update(sql, id);
  }
}
