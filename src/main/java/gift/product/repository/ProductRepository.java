package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.juli.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("Product")
            .usingGeneratedKeyColumns("id");
    }

    public Product save(Product product, LoginMember loginMember) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", loginMember.memberId());
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("imageUrl", product.getImageUrl());

        Long productId = (Long) simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Product(productId, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public List<Product> findAll(LoginMember loginMember) {
        var sql = "SELECT id, name, price, imageUrl FROM Product WHERE member_id = ?";

        return jdbcTemplate.query(sql, getProductRowMapper(), loginMember.memberId());
    }

    public Product findById(Long id, LoginMember loginMember) throws DataAccessException {
        var sql = "SELECT id, name, price, imageUrl FROM Product WHERE member_id = ? AND id = ?";

        return jdbcTemplate.queryForObject(sql, getProductRowMapper(), loginMember.memberId(), id);
    }

    public void update(Product product, LoginMember loginMember) {
        var sql = "UPDATE Product SET name = ?, price = ?, imageUrl = ? WHERE member_id = ? AND id = ?";

        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(),
            loginMember.memberId(), product.getId());
    }

    public void delete(Long id, LoginMember loginMember) {
        var sql = "DELETE FROM Product WHERE member_id = ? AND id = ?";

        jdbcTemplate.update(sql, loginMember.memberId(), id);
    }

    private RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("imageUrl")
        );
    }
}
