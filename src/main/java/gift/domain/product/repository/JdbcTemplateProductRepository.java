package gift.domain.product.repository;

import gift.domain.product.Product;
import gift.global.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 해당 이름의 상품 DB 에 존재 여부 확인
     */
    public boolean existsByProductName(String name) {
        String sql = "SELECT COUNT(*) FROM product WHERE name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);

        if (count == 1) {
            return true;
        }
        return false;
    }

    /**
     * 상품 추가
     */
    public void createProduct(Product product) {
        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";

        int rowNum = jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImageUrl());

        if (rowNum == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "상품 추가에 실패했습니다.");
        }
    }

    /**
     * 전체 상품 목록 조회
     */
    public List<Product> getProducts() {
        String sql = "SELECT * FROM product ORDER BY ID ASC";

        List<Product> products = jdbcTemplate.query(sql,
            BeanPropertyRowMapper.newInstance(Product.class));

        return products;
    }

    /**
     * 상품 수정
     */
    public void updateProduct(Long id, Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";

        int rowNum = jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImageUrl(), id);
        if (rowNum == 0) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "상품 수정에 실패했습니다.");
        }
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";

        int rowNum = jdbcTemplate.update(sql, id);
        if (rowNum == 0) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "상품 삭제에 실패했습니다.");
        }
    }

    /**
     * 해당 ID 리스트에 속한 상품들 삭제
     */
    public void deleteProductsByIds(List<Long> productIds) {
        String placeholders = productIds.stream()
            .map(id -> "?")
            .collect(Collectors.joining(", "));

        String sql = "DELETE FROM product WHERE id IN (" + placeholders + ")";

        Object[] params = productIds.toArray();

        int rowNum = jdbcTemplate.update(sql, params);
        if (productIds.size() != rowNum) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "선택된 상품들 삭제에 실패했습니다.");
        };
    }

    /**
     * 해당 ID 리스트에 속한 상품들 조회
     */
    public List<Product> getProductsByIds(List<Long> productIds) {
        String placeholders = productIds.stream()
            .map(id -> "?")
            .collect(Collectors.joining(", "));

        String sql = "SELECT * FROM product WHERE id IN (" + placeholders + ")";

        Object[] params = productIds.toArray();

        List<Product> products = jdbcTemplate.query(sql,
            BeanPropertyRowMapper.newInstance(Product.class), params);

        if(products.size() != productIds.size()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "선택된 상품들 조회에 실패했습니다.");
        }

        return products;
    }


}
