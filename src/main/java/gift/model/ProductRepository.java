package gift.model;

import gift.dto.ProductDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> productRowMapper = (ResultSet, rowNum) ->
        new Product(
                ResultSet.getLong("id"),
                ResultSet.getString("name"),
                ResultSet.getInt("price"),
                ResultSet.getString("imageUrl")
            );

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    public Optional<Product> findById(Long id) {
        try {
            Product product= jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", productRowMapper, id);
            return Optional.ofNullable(product);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Product save(ProductDTO productDTO) {
        Map<String, Object> parameters = Map.of(
                "name", productDTO.getName(),
                "price", productDTO.getPrice(),
                "imageUrl", productDTO.getImageUrl()
        );
        Long newId = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Product(newId, productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl());
    }

    public void update(Long id, ProductDTO productDTO) {
        jdbcTemplate.update("UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
                productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl(), id);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM wish_lists WHERE productId = ?", id);
        jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }

    public void validateKaKaoName(String name) {
        if (name.contains("카카오") || name.equalsIgnoreCase("kakao")) {
            throw new IllegalArgumentException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에 사용 가능합니다.");
        }
    }
}
