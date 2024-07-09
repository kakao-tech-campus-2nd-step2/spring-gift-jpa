package gift.Repository;

import gift.Model.Product;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);

    public int addWishlistFromProduct(Product product){
        return jdbcTemplate.update("INSERT INTO wishlist (name, price, imageUrl) VALUES (?,?,?)",product.getName(),product.getPrice(),product.getImageUrl());
    }

    public List<Product> findAllWishlist() {
        return jdbcTemplate.query("SELECT * FROM wishlist", rowMapper);
    }

    public int deleteWishlistById(Long id){
        return jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }
}
