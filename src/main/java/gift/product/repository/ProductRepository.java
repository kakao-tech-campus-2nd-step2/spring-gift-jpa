package gift.product.repository;

import gift.product.dto.ProductResponseDto;
import gift.product.entity.Product;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create 처리 메서드
    public void insertProduct(Product product) {

        // 이미 존재하는 id에 넣으려고 하는지 검증
        long productId = product.getProductId();
        verifyProductAlreadyExist(productId);

        var sql = """
            insert into products (product_id, name, price, image) values (?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql, product.getProductId(), product.getName(),
            product.getPrice(), product.getImage());
    }

    // Read 처리 메서드
    public List<ProductResponseDto> selectProducts() {
        var sql = """
            select product_id, name, price, image
            from products;
            """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ProductResponseDto(
            resultSet.getLong("product_id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
        ));
    }

    // Update 처리 메서드
    public void updateProduct(Product product) {
        var sql = """
            update products set name = ?, price = ?, image = ? where product_id = ?;
            """;

        jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImage(), product.getProductId());
    }

    // Delete 처리 메서드
    public void deleteProduct(long productId) {
        // 우선 해당하는 id가 있는지부터 검사
        verifyProductExist(productId);

        var sql = """
            delete from products where product_id = ?;
            """;

        jdbcTemplate.update(sql, productId);
    }

    // db에서 특정 id를 갖는 로우를 반환
    public ProductResponseDto selectProduct(long productId) {
        // 존재하는 id를 불러올 수 있도록 검증
        verifyProductExist(productId);

        var sql = """
            select product_id, name, price, image
            from products
            where product_id = ?;
            """;

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new ProductResponseDto(
            resultSet.getLong("product_id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
        ), productId);
    }

    // db에서 특정 id를 갖는 로우가 있는지 확인하는 메서드
    private boolean exists(long productId) {
        var sql = """
            select product_id
            from products
            where product_id = ?;
            """;

        // 결과의 로우가 존재하는지 반환
        boolean isEmpty = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> resultSet.getInt("product_id"), productId)
            .isEmpty();

        return !isEmpty;
    }

    // 논리적 검증: 삽입 시에는 id가 중복되지 않아야 한다.
    private void verifyProductAlreadyExist(long productId) {
        if (exists(productId)) {
            // 의미있는 메시지를 위해 id를 함께 제공
            throw new IllegalArgumentException("id가 중복됩니다. (id: " + productId + ")");
        }
    }

    // 논리적 검증: 조회 시에는 id가 존재해야 한다.
    private void verifyProductExist(long productId) {
        if (!exists(productId)) {
            // 의미있는 메시지를 위해 id를 함께 제공
            throw new NoSuchElementException("해당 id를 가진 제품이 존재하지 않습니다. (id: " + productId + ")");
        }
    }
}
