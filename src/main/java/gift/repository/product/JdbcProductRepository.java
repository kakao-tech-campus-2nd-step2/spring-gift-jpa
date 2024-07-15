package gift.repository.product;
/*
import gift.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL 쿼리 상수
    private static final String SELECT_ALL = "SELECT * FROM product";
    private static final String SELECT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO product (name, price, description, image_url) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, price = ?, description = ?, image_url = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";

    // RowMapper를 람다로 변경
    private static final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("price"),
            rs.getString("description"),
            rs.getString("image_url")
    );

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT_ALL, productRowMapper);
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            Product product = jdbcTemplate.queryForObject(SELECT_BY_ID, productRowMapper, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Product product) {
        jdbcTemplate.update(INSERT_PRODUCT, product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl());
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT, product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }
}


 */