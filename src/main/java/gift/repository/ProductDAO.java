package gift.repository;

import gift.model.ProductRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductRecord> getAllRecords() {
        String sql = "select * from products";

        return jdbcTemplate.query(sql, (record,rowNum) -> new ProductRecord(
                record.getLong("id"),
                record.getString("name"),
                record.getInt("price"),
                record.getString("imageUrl")
                )
            );
    }

    public ProductRecord getRecord(long id) {
        if (!isRecordExist(id)) {
            throw new NoSuchElementException();
        }

        String sql = "select * from products where id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ProductRecord(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")
        ), id);
    }

    public ProductRecord addNewRecord(ProductRecord product) {
        long id = insertWithGeneratedKey(product.name(), product.price(), product.imageUrl());

        return product.withId(id);
    }

    public ProductRecord addNewRecord(ProductRecord product, long id) throws DuplicateKeyException {
        if (isRecordExist(id)) {
            throw new DuplicateKeyException("A record with the given ID already exists.");
        }

        ProductRecord record = product.withId(id);

        String sql = "insert into products values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, record.id(), record.name(), record.price(), record.imageUrl());

        return record;
    }

    public ProductRecord replaceRecord(long id, ProductRecord product) throws NoSuchElementException {
        if (!isRecordExist(id)) {
            throw new NoSuchElementException("Record not found");
        }

        ProductRecord record = product.withId(id);

        String sql = "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, record.name(), record.price(), record.imageUrl(), record.id());

        return record;
    }

    public ProductRecord updateRecord(long id, ProductRecord patch) throws NoSuchElementException {
        if (!isRecordExist(id)) {
            throw new NoSuchElementException("Record not found");
        }

        ProductRecord record = getRecord(id).getUpdatedRecord(patch);

        String sql = "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, record.name(), record.price(), record.imageUrl(), record.id());

        return record;
    }

    public void deleteRecord(long id) throws NoSuchElementException {
        if (!isRecordExist(id)) {
            throw new NoSuchElementException("Record not found");
        }

        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    public boolean isRecordExist(long id) {
        String sql = "select count(*) from products where id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        if (count > 0) {
            return true;
        }

        return false;
    }

    private long insertWithGeneratedKey(String name, int price, String imageUrl) {
        String insertSql = "insert into products(name, price, imageUrl) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setString(3, imageUrl);

            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

}
