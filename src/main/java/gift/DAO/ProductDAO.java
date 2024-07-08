package gift.DAO;

import gift.dto.ProductDTO;
import gift.exception.NoSuchProductException;
import java.util.Collection;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ProductDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("PRODUCT")
            .usingGeneratedKeyColumns("id");
    }

    public Collection<ProductDTO> getProducts() {
        var sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    public ProductDTO getProduct(Long id) {
        var sql = "SELECT * FROM PRODUCT WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new NoSuchProductException();
        }
    }

    public long addProduct(ProductDTO productDTO) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(productDTO);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public void updateProduct(ProductDTO productDTO) {
        var sql = "UPDATE PRODUCT SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, productDTO.name(), productDTO.price(), productDTO.imageUrl(), productDTO.id());
    }

    public void deleteProduct(long id) {
        var sql = "DELETE FROM PRODUCT WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<ProductDTO> productRowMapper() {
        return (resultSet, rowNum) -> new ProductDTO(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("imageUrl"));
    }
}
