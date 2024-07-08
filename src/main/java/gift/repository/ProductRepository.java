package gift.repository;

import gift.DTO.ProductDTO;
import gift.domain.Product;
import gift.domain.Product.CreateProduct;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<ProductDTO> getList() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));
    }

    public ProductDTO getProduct(Long id) {
        String sql = "SELECT * FROM Product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProductDTO.class), id);
    }

    public int createProduct(CreateProduct create) {
        String sql = "INSERT INTO Product (name, price, imageUrl) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, create.getName());
            ps.setInt(2, create.getPrice());
            ps.setString(3, create.getImageUrl());
            return ps;
        }, keyHolder) > 0) {
            return keyHolder.getKey().intValue();
        } else {
            return -1;
        }
    }

    public int updateProduct(Long id, Product.UpdateProduct update) {
        String sql = "UPDATE Product SET id = ?, name = ?, price = ?, imageUrl = ? WHERE id = ?";
        if (jdbcTemplate.update(sql, id, update.getName(), update.getPrice(),
            update.getImageUrl(), id) == 1) {
            return id.intValue();
        }
        return -1;
    }

    public int deleteProduct(Long id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 1) {
            return id.intValue();
        }
        return -1;

    }

    public boolean validateId(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM Product WHERE id = ?)";
        if (jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class) == 1) {

            return true;
        }
        return false;
    }
}
