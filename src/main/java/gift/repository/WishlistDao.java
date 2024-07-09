package gift.repository;

import gift.vo.WishProduct;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JdbcClient를 이용한 DB 접속
 */
@Repository
public class WishlistDao {

    private final JdbcClient jdbcClient;

    public WishlistDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<WishProduct> getWishProductList(String memberEmail) {
        String sql = "SELECT * FROM WISH_PRODUCT WHERE member_email = :member_email";
        return jdbcClient.sql(sql)
                .param("member_email", memberEmail)
                .query(new WishProductMapper()).list();
    }

    public Boolean addWishProduct(String memberEmail, Long productId) {
        String sql = "INSERT INTO WISH_PRODUCT (member_email, product_id) " +
                "VALUES (:member_email, :product_id)";
        int resultRowNum = this.jdbcClient.sql(sql)
                .param("member_email", memberEmail)
                .param("product_id", productId)
                .update();
        return resultRowNum == 1;
    }

    public Boolean deleteWishProduct(String memberEmail, Long productId) {
        String sql = "DELETE FROM WISH_PRODUCT " +
                "WHERE member_email = :member_email AND product_id = :product_id";
        int resultRowNum = this.jdbcClient.sql(sql)
                .param("member_email", memberEmail)
                .param("product_id", productId)
                .update();
        return resultRowNum == 1;
    }

}
