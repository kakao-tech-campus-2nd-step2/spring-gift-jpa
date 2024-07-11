package gift.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    // 상품 jdbc 연결
    @Autowired
    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    // 상품 저장
    public void save(Product product) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.name());
        parameters.put("price", product.price());
        parameters.put("imgUrl", product.imgUrl());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        product.id();
    }

    // 상품 모두 조회
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imgUrl")
        ));
    }

    // id로 상품 조회
    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imgUrl")
        ));
    }

    // 상품 상세정보 수정
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, imgUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imgUrl(), product.id());
    }

    // 상품 삭제
    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}