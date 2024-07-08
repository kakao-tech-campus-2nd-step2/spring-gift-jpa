package gift.repository.product;


import gift.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductJdbcRepository implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product(name, price, imageUrl) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT id, name, price, imageUrl FROM product WHERE id = ?";
        List<Product> product = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")
        ));

        if (product.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(product.getFirst());
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, price, imageUrl FROM product";
        List<Product> products = jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")
        ));

        return products;
    }

    //interface return type 으로 인해 Long type return
    @Override
    public Long update(Long id, Product product){
        String sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        int updatedRow = jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
        return (long) updatedRow;
    }

    //interface return type 으로 인해 Long type return
    @Override
    public Long delete(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        int deletedRow = jdbcTemplate.update(sql, id);
        return (long) deletedRow;
    }
}
