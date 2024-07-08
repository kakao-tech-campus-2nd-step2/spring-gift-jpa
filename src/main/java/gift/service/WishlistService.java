package gift.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.exception.InvalidProductException;
import gift.model.User;
import gift.model.Wishlist;

@Service
public class WishlistService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AuthService authServicee;
	
	public List<Wishlist> getWishlist(String token, BindingResult bindingResult) {
        String email = authServicee.parseToken(token);
        User user = authServicee.searchUser(email, bindingResult);
		String sql = "SELECT w.id, p.name as productName, w.quantity FROM wishlist w JOIN products p ON w.product_id = p.id WHERE w.user_id = ?";
        
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, user.getId());
            }
        };
        
        RowMapper<Wishlist> rowMapper = new RowMapper<Wishlist>() {
            @Override
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Wishlist wishlist = new Wishlist();
            	wishlist.setId(rs.getLong("id"));
            	wishlist.setProductName(rs.getString("productName"));
            	wishlist.setQuantity(rs.getInt("quantity"));
                return wishlist;
            }
        };

        return jdbcTemplate.query(sql, pss, rowMapper);
    }
	
	public void addWishlist(String token, Wishlist wishlist, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
		}
		String email = authServicee.parseToken(token);
        User user = authServicee.searchUser(email, bindingResult);
		String checkProductSql = "SELECT COUNT(*) FROM products WHERE name = ?";
		
		Integer count = jdbcTemplate.queryForObject(checkProductSql, new Object[]{wishlist.getProductName()}, Integer.class);
		if (count==null || count==0) {
			throw new InvalidProductException("The product does not exist.");
		}
		String sql = "INSERT INTO wishlist (user_id, product_id, quantity) VALUES (?, (SELECT id FROM products WHERE name = ?), ?)";
		
		int rowsAffected = jdbcTemplate.update(sql, user.getId(), wishlist.getProductName(), wishlist.getQuantity());
		if(rowsAffected == 0) {
			throw new InvalidProductException("Product colud not be added to wishlist.");
		}
	}
	
	public void removeWishlist(String token, Wishlist wishlist, BindingResult bindingResult) {
		String email = authServicee.parseToken(token);
        User user = authServicee.searchUser(email, bindingResult);
		String sql = "DELETE FROM wishlist WHERE user_id = ? AND product_id = (SELECT id FROM products WHERE name = ?)";
		
		int rowsAffected = jdbcTemplate.update(sql, user.getId(), wishlist.getProductName());
		if(rowsAffected == 0) {
			throw new InvalidProductException("Product could not be be removed from wishlist.");
		}
	}
	
	public void updateWishlistQuantity(String token, Wishlist wishlist, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
		}
		String email = authServicee.parseToken(token);
        User user = authServicee.searchUser(email, bindingResult);
		if(wishlist.getQuantity() == 0) {
			removeWishlist(token, wishlist, bindingResult);
		}
		String sql = "UPDATE wishlist SET quantity = ? WHERE user_id = ? AND product_id = (SELECT id FROM products WHERE name =?)";
		
		int rowsAffected =  jdbcTemplate.update(sql, wishlist.getQuantity(), user.getId(), wishlist.getProductName());
		if(rowsAffected == 0) {
			throw new InvalidProductException("Product quantity could not be updated.");
		}
	}
}
