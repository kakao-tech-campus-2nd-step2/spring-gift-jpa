//package gift.dao;
//
//import gift.model.Product;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ProductDao {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public ProductDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Product> selectAllProduct() {
//        String sql = "SELECT * FROM product";
//        return jdbcTemplate.query(sql, productRowMapper());
//    }
//
//    public Product selectProduct(Long id) {
//        String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";
//        return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
//    }
//
//    public void insertProduct(Product product) {
//        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
//    }
//
//    public void deleteProduct(Long id) {
//        String sql = "DELETE FROM product WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    public void updateProduct(Product product) {
//        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
//        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
//    }
//
//    private RowMapper<Product> productRowMapper() {
//        return (resultSet, rowNum) -> new Product(
//                resultSet.getLong("id"),
//                resultSet.getString("name"),
//                resultSet.getInt("price"),
//                resultSet.getString("image_url")
//        );
//    }
//}