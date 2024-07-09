package gift.dao;

import gift.vo.Product;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JdbcClient를 이용한 DB 접속
 */
@Repository
public class ProductDao {

    private final JdbcClient jdbcClient;

    public ProductDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Product> getProducts() {
        String sql = "SELECT * FROM product";
        return jdbcClient.sql(sql).query(new ProductMapper()).list();
    }

    public Product getProductById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new ProductMapper()).single();
    }

    public Boolean addProduct(Product product) {
        String sql = "INSERT INTO PRODUCT (name, price, image_url) "
                + "VALUES (:name, :price, :image_url)";
        int resultRowNum = this.jdbcClient.sql(sql)
                .param("name", product.getName())
                .param("price", product.getPrice())
                .param("image_url", product.getImageUrl())
                .update();
        return resultRowNum == 1;
    }

    public Boolean updateProduct(Product product) {
        String sql = "UPDATE PRODUCT SET name = :name, price = :price, image_url = :image_url "
                + "WHERE id = :id";
        int resultRowNum = this.jdbcClient.sql(sql)
                .param("name", product.getName())
                .param("price", product.getPrice())
                .param("image_url", product.getImageUrl())
                .param("id", product.getId())
                .update();
        return resultRowNum == 1;
    }

    public Boolean deleteProduct(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        int resultRowNum = this.jdbcClient.sql(sql)
                .param(id)
                .update();
        return resultRowNum == 1;

    }

}
