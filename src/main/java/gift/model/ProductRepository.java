package gift.model;

import gift.exception.ProductNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 상품 데이터에 대한 데이터베이스 접근 객체(DAO)임. 이 클래스는 상품의 CRUD 연산을 처리함.
 */
@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * ProductDao의 생성자임.
     *
     * @param jdbcTemplate 데이터베이스 연산을 위한 JdbcTemplate 객체
     */
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Product> productRowMapper = (rs, rowNum) ->
        new Product(rs.getLong("id"), rs.getString("name"),
            rs.getLong("price"), rs.getString("image_url"));

    /**
     * 새로운 상품을 생성함.
     *
     * @param product 생성할 상품 정보
     * @return 생성된 상품 객체
     */
    public Product createProduct(Product product) {
        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imageUrl());
        return product;
    }

    /**
     * 지정된 ID의 상품을 조회함.
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품 객체
     */
    public Product getProduct(Long id) {
        productNotFoundDetector(id);
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productRowMapper, id);
    }

    public void productNotFoundDetector(Long id) {
        if (!exists(id)) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }

    /**
     * 모든 상품을 조회함.
     *
     * @return 모든 상품 객체의 리스트
     */
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    /**
     * 지정된 ID의 상품을 삭제함.
     *
     * @param id 삭제할 상품의 ID
     */
    public void deleteProduct(Long id) {
        productNotFoundDetector(id);
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * 지정된 ID의 상품을 업데이트함.
     *
     * @param id      업데이트할 상품의 ID
     * @param product 업데이트할 상품 정보
     * @return 업데이트된 상품 객체
     */
    public Product updateProduct(Long id, Product product) {
        productNotFoundDetector(id);
        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imageUrl(), id);
        return getProduct(id);
    }

    /**
     * 지정된 ID의 상품이 존재하는지 확인함.
     *
     * @param id 확인할 상품의 ID
     * @return 상품 존재 여부
     */
    public boolean exists(Long id) {
        String sql = "SELECT COUNT(*) FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }
}