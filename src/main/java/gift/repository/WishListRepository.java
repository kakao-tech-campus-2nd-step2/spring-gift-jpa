package gift.repository;

import gift.domain.Product;
import gift.domain.User;
import gift.domain.WishProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * DB에 위시리스트 내용을 입력받아 저장
     */
    public void save(WishProduct wishProduct){
        var sql = "INSERT INTO wishList(userId, productId) VALUES (?,?)";
        jdbcTemplate.update(sql, wishProduct.getUserId(), wishProduct.getProductId());
    }
    /*
     * DB에 저장된 모든 위시리스트 내용을 반환
     */
    public List<WishProduct> findAll(){
        String sql = "select userId, productId from wishList";
        List<WishProduct> list = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    WishProduct wishProduct = new WishProduct(
                            resultSet.getString("userId"),
                            resultSet.getLong("productId")
                    );
                    return wishProduct;
                });
        return list;
    }
    /*
     * DB에서 userId 열을 기준으로 위시리스트를 반환
     */
    public List<WishProduct> findByUserId(String userId) {
        String sql = "select userId, productId from wishList where userId = ?";
        List<WishProduct> list = jdbcTemplate.query(
                sql, new Object[]{userId}, (resultSet, rowNum) ->{
                    WishProduct wishProduct = new WishProduct(
                            resultSet.getString("userId"),
                            resultSet.getLong("productId")
                    );
                    return wishProduct;
                });
        return list;
    }
    /*
     * DB에서 userId와 productId 열을 기준으로 위시리스트 내용을 삭제
     */
    public void delete(String userId, Long productId){
        String sql = "DELETE FROM wishList WHERE userId = ? AND productId = ?";
        jdbcTemplate.update(sql, userId, productId);
    }
}
