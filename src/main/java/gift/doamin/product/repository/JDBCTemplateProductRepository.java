package gift.doamin.product.repository;

import gift.doamin.product.entity.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCTemplateProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JDBCTemplateProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
        String sql = """
            create table product(
               id bigint auto_increment,
               userId bigint,
               name varchar(255),
               price int,
               imageUrl varchar(255),
               primary key(id) 
            )
            """;
        jdbcTemplate.execute(sql);
    }

    @Override
    public Product insert(Product product) {
        Long userId = product.getUserId();
        String name = product.getName();
        Integer price = product.getPrice();
        String imageUrl = product.getImageUrl();

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("name", name)
            .addValue("price", price)
            .addValue("imageUrl", imageUrl);
        Long id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        product.setId(id);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", productRowMapper());
    }

    @Override
    public Product findById(Long id) {
        var sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(
            sql, productRowMapper(), id
        );
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(),
            product.getId());
        return product;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM product WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long userId = rs.getLong("userId");
            String name = rs.getString("name");
            Integer price = rs.getInt("price");
            String imageUrl = rs.getString("imageUrl");
            Product product = new Product(name, price, imageUrl);
            product.setId(id);
            product.setUserId(userId);
            return product;
        };
    }
}
