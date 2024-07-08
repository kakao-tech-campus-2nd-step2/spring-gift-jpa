package gift.repository;

import gift.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcWishlistRepository implements WishlistRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcWishlistRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Wishlist save(Wishlist wishlist) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO wishlist (member_id, product_name, product_url) VALUES (?, ?, ?)";

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setLong(1, wishlist.getMemberId());
      ps.setString(2, wishlist.getProductName());
      ps.setString(3, wishlist.getProductUrl());
      return ps;
    }, keyHolder);

    wishlist.setId(keyHolder.getKey().longValue());
    return wishlist;
  }

  @Override
  public List<Wishlist> findByMemberId(Long memberId) {
    String sql = "SELECT * FROM wishlist WHERE member_id = ?";
    return jdbcTemplate.query(sql, new Object[]{memberId}, (rs, rowNum) -> {
      Wishlist wishlist = new Wishlist();
      wishlist.setId(rs.getLong("id"));
      wishlist.setMemberId(rs.getLong("member_id"));
      wishlist.setProductName(rs.getString("product_name"));
      wishlist.setProductUrl(rs.getString("product_url"));
      return wishlist;
    });
  }

  @Override
  public void deleteById(Long id) {
    String sql = "DELETE FROM wishlist WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
