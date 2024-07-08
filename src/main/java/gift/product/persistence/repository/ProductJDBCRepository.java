package gift.product.persistence.repository;

import gift.product.persistence.entity.Product;
import gift.global.exception.ErrorCode;
import gift.global.exception.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductJDBCRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM product WHERE id = ?",
                (rs, rowNum) -> new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("price"),
                    rs.getString("url")
                ),
                id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Product with id " + id + " not found");
        }
    }

    @Override
    public Long saveProduct(Product product) {
        var sql = "INSERT INTO product (name, description, price, url) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            con -> {
                var ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setInt(3, product.getPrice());
                ps.setString(4, product.getUrl());
                return ps;
            },
            keyHolder
        );

        return keyHolder.getKey().longValue();
    }


    @Override
    public Long updateProduct(Long id, Product product) {
        var sql = "UPDATE product SET name = ?, description = ?, price = ?, url = ? WHERE id = ?";

        int affectedRows = jdbcTemplate.update(sql,
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getUrl(),
            id);

        if (affectedRows == 0) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Product with id " + id + " not found");
        }

        return id;
    }

    @Override
    public Long deleteProductById(Long id) {
        var sql = "DELETE FROM product WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        if (affectedRows == 0) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Product with id " + id + " not found");
        }
        return id;
    }

    @Override
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(
            "SELECT * FROM product",
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("url")
            )
        );
    }

    @Override
    public void deleteProductByIdList(List<Long> productIds) {
        StringBuilder sql = new StringBuilder("DELETE FROM product WHERE id IN (");
        for (int i = 0; i < productIds.size(); i++) {
            sql.append("?");
            if (i < productIds.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        jdbcTemplate.update(sql.toString(), productIds.toArray());
    }

    @Override
    public Map<Long, Product> getProductsByIds(List<Long> productIds) {
        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE id IN (");
        for (int i = 0; i < productIds.size(); i++) {
            sql.append("?");
            if (i < productIds.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        return jdbcTemplate.query(sql.toString(), rs -> {
            Map<Long, Product> resultMap = new HashMap<>();
            while (rs.next()) {
                resultMap.put(rs.getLong("id"),
                    new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("url")
                    )
                );
            }
            return resultMap;
        }, productIds.toArray());
    }
}
