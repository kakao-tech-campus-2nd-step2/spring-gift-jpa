package gift.Repository;

import gift.DTO.ProductDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {
  JdbcTemplate jdbcTemplate;
  public WishListDao(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate=jdbcTemplate;
  }

  public void insertWishList(ProductDto wishProduct){
    var sql = """
      INSERT INTO wishList INTO VALUES(?, ?, ?);
      """;
    jdbcTemplate.update(sql,wishProduct.getName(),wishProduct.getPrice(),wishProduct.getImageUrl());
  }


}
