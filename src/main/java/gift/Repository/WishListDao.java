package gift.Repository;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {
  JdbcTemplate jdbcTemplate;
  public WishListDao(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate=jdbcTemplate;
  }

  public void insertWishList(ProductDto wishProduct){
    var sql = """
      INSERT INTO wishList(name,price,imageUrl) VALUES(?, ?, ?);
      """;
    jdbcTemplate.update(sql,wishProduct.getName(),wishProduct.getPrice(),wishProduct.getImageUrl());
  }


  public List<ProductDto> selectWishList(UserDto user) {
    var sql = """
      SELECT * FROM wishList;
      """;
    return jdbcTemplate.query(sql,wishListRowMapper());
  }

  private RowMapper<ProductDto> wishListRowMapper(){
    return (rs,rowNum)->new ProductDto(
      rs.getLong("id"),
      rs.getString("name"),
      rs.getInt("price"),
      rs.getString("imageUrl")
    );
  }
}
