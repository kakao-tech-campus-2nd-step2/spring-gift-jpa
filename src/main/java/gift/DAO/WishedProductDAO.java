package gift.DAO;

import gift.dto.WishedProductDTO;
import java.util.Collection;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class WishedProductDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public WishedProductDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("WISHED_PRODUCT");
    }

    public Collection<WishedProductDTO> getWishedProducts(String memberEmail) {
        var sql = "SELECT * FROM WISHED_PRODUCT WHERE member_email = ?";
        return jdbcTemplate.query(sql, wishedProductRowMapper(), memberEmail);
    }

    public void addWishedProduct(WishedProductDTO wishedProductDTO) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(wishedProductDTO);
        simpleJdbcInsert.execute(parameters);
    }

    public void deleteWishedProduct(WishedProductDTO deletedWishedProductDTO) {
        var sql = "DELETE FROM WISHED_PRODUCT WHERE member_email = ? AND product_id = ?";
        jdbcTemplate.update(sql, deletedWishedProductDTO.memberEmail(), deletedWishedProductDTO.productId());
    }

    public void updateWishedProduct(WishedProductDTO updatedWishedProductDTO) {
        var sql = "UPDATE WISHED_PRODUCT SET amount = ? WHERE member_email = ? AND product_id = ?";
        jdbcTemplate.update(sql, updatedWishedProductDTO.amount(), updatedWishedProductDTO.memberEmail(), updatedWishedProductDTO.productId());
    }

    private RowMapper<WishedProductDTO> wishedProductRowMapper() {
        return (resultSet, rowNum) -> new WishedProductDTO(
            resultSet.getString("member_email"),
            resultSet.getLong("product_id"),
            resultSet.getInt("amount"));
    }
}
