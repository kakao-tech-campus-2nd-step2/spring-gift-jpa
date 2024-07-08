package gift.repository;

import gift.entity.Product;
import gift.exceptionhandler.DatabaseAccessException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 안 되면 DataSoruce
    @Autowired
    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // DuplicatedKeyException
    public Boolean save(@Valid Product product){
        try {
            String sql = "INSERT INTO products(id, name, price, imageUrl) VALUES (?,?,?,?)";
            jdbcTemplate.update(sql, product.id(), product.name(), product.price(), product.imageUrl());
        }catch(Exception e){
            throw new DatabaseAccessException("상품 삽입 문제");
        }
        return true;
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from products";
        List<Product> products = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Product product = new Product(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getString("imageUrl")
                    );
                    return product;
                });
        return products;
    }

    @Override
    public Optional<Product> findById(long id) {
        String sql = "select * from products where id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    (resultSet, rowNum) -> new Product(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getString("imageUrl")
                    )
            );
            return Optional.ofNullable(product);
        } catch (Exception e) {
            throw new DatabaseAccessException("상품 조회 문제");
        }
    }

    @Override
    public Boolean updateById(long id, @Valid Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        try {
            int affectedRows = jdbcTemplate.update(
                    sql,
                    product.name(),
                    product.price(),
                    product.imageUrl(),
                    id
            );
            if (affectedRows > 0) return true;
            throw new DatabaseAccessException("상품 update 문제");
        }catch (Exception e) {
            throw new DatabaseAccessException("상품 update 문제");
        }
    }

    @Override
    public Boolean deleteById(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try {
            int rowsDeleted = jdbcTemplate.update(sql, id);
            if(rowsDeleted > 0) return true;
            throw new DatabaseAccessException("상품 delete 문제");
        } catch (Exception e) {
            throw new DatabaseAccessException("상품 delete 문제");
        }
    }


}
