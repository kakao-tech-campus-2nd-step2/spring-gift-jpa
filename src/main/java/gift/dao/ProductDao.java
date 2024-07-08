package gift.dao;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Component
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 데이터베이스 반환결과인 Result Set 객체로 변환
    public RowMapper<Product> ProductRowMapper() {
        return (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    }

    // 모든 상품 리스트 반환
    public List<Product> selectAllProduct() {
        var sql = "select * from product";
        return jdbcTemplate.query(sql, ProductRowMapper());
    }

    // 상품 하나 반환
    public Product selectProduct(Long id) {
        var sql = "select id, name, price, image_url from product where id = ?";
        return jdbcTemplate.queryForObject(sql, ProductRowMapper(), id);
    }

    // 상품 추가
    public void insertProduct(Product product) {
        var sql = "insert into product (name, price, image_url) values (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        var sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 상품 업데이트
    public void updateProduct(Product product) {
        var sql = "update product set name = ?, price = ?, image_url = ? where id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }
}
