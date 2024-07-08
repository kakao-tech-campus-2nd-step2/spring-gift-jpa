package gift.repository;

import gift.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     * DB에 저장된 모든 Product 정보를 가져와 반환
     */
    public List<Product> findAll(){
        String sql = "select id, name, price, imageUrl from products";
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
    /*
     * DB에 저장된 모든 Product의 ID 정보를 가져와 반환
     */
    public List<Long> findAllId(){
        String sql = "select id from products";
        List<Long> ids = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Long id = resultSet.getLong("id");
                    return id;
                });
        return ids;
    }
    /*
     * DB에 저장된 Product 중, ID를 기준으로 하나를 선택하여 반환
     */
    public Product findById(Long id){
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
            Product productEntity = new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("imageUrl")
            );
            return productEntity;
        });
        return product;
    }
    /*
     * DB에 Product 정보를 받아 저장
     */
    public void save(Product product){
        var sql = "INSERT INTO products(id, name, price, imageUrl) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
    /*
     * DB에 있는 Product를 ID를 기준으로 찾아 삭제
     */
    public void delete(Long id){
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    /*
     * DB에 있는 Product 정보를 새로운 Product 정보로 갱신
     */
    public void update(Product product, Long id){
        String sql = "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }
}
