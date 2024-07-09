package gift.service;

import gift.exception.InvalidProductException;
import gift.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void setup() {
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    private static final class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imageUrl = rs.getString("imageUrl");
            return new Product(id, name, price, imageUrl);
        }
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    public Product getProduct(long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
        	return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductMapper());
        } catch(EmptyResultDataAccessException e) {
        	throw new InvalidProductException("Product not found with id: " + id);
        }  
    }

    public Product createProduct(Product product, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("imageUrl", product.getImageUrl());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);
        return new Product(newId.longValue(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void updateProduct(long id, Product updatedProduct, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
    	}
    	if(updatedProduct.getId()==null || !updatedProduct.getId().equals(id)) {
    		throw new InvalidProductException("Product Id mismatch.");
    	}
        String sql = "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        int rowsAffected =  jdbcTemplate.update(sql, updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getImageUrl(), updatedProduct.getId());
        if(rowsAffected == 0) {
        	throw new InvalidProductException("Product not found with id: " + id);
        }
    }

    public void deleteProduct(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if(rowsAffected == 0) {
        	throw new InvalidProductException("Product not found with id: " + id);
        }
    }
}
