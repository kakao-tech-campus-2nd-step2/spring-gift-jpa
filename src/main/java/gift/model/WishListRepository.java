package gift.model;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * WishListDAO 클래스는 WishList 객체를 관리하는 DAO 클래스입니다. DB를 이용해 WishList 객체를 관리할 수 있습니다
 */
@Repository
public class WishListRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertWishList;

    /**
     * WishListDAO 생성자
     *
     * @param jdbcTemplate JDBC 템플릿 객체
     */
    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.insertWishList = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("wishlist")
            .usingGeneratedKeyColumns("id");
    }

    /**
     * 새로운 WishList를 생성함
     *
     * @param productId WishList에 추가할 상품의 ID
     * @param userId    WishList에 추가할 사용자의 ID
     * @return 생성된 WishList 객체
     */
    public WishList createWishList(long productId, long userId) {
        WishList wishList = new WishList(productId, userId);
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(wishList);
        Number newId = insertWishList.executeAndReturnKey(parameters);
        return new WishList(newId.longValue(), wishList.getProductId(), wishList.getUserId());
    }

    /**
     * 지정된 사용자의 모든 WishList를 조회함
     *
     * @param userId 조회할 사용자의 ID
     * @return 지정된 사용자의 모든 WishList 객체의 리스트
     */
    public List<WishList> getWishListsByUserId(long userId) {
        String sql = "SELECT * FROM wishlist WHERE user_id = :userId";
        return jdbcTemplate.query(sql,
            new MapSqlParameterSource("userId", userId),
            (rs, rowNum) -> new WishList(rs.getLong("id"), rs.getLong("product_id"),
                rs.getLong("user_id")));
    }

    /**
     * 모든 WishList를 조회
     *
     * @return 모든 WishList 객체의 리스트
     */
    public List<WishList> getAllWishLists() {
        String sql = "SELECT * FROM wishlist";
        return jdbcTemplate.query(sql,
            (rs, rowNum) -> new WishList(rs.getLong("id"), rs.getLong("product_id"),
                rs.getLong("user_id")));
    }

    /**
     * 지정된 사용자의 모든 WishList를 삭제
     *
     * @param userId 삭제할 사용자의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteWishListsByUserId(Long userId) {
        String sql = "DELETE FROM wishlist WHERE user_id = :userId";
        int rowsAffected = jdbcTemplate.update(sql, new MapSqlParameterSource("userId", userId));
        return rowsAffected > 0;
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에서 삭제
     *
     * @param userId    삭제할 사용자의 ID
     * @param productId 삭제할 상품의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteWishListByUserIdAndProductId(Long userId, Long productId) {
        String sql = "DELETE FROM wishlist WHERE user_id = :userId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productId", productId);
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에 추가
     *
     * @param userId    추가할 사용자의 ID
     * @param productId 추가할 상품의 ID
     * @return 추가 성공 여부
     */
    public boolean addWishListByUserIdAndProductId(Long userId, Long productId) {
        String sql = "INSERT INTO wishlist (product_id, user_id) VALUES (:productId, :userId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productId", productId);
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    /**
     * 사용자의 위시리스트 존재 여부 확인
     *
     * @param userId 확인할 사용자의 ID
     * @return 위시리스트 존재 여부
     */
    public boolean isExist(long userId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM wishlist WHERE user_id = :userId)";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("userId", userId), Boolean.class);
    }

    /**
     * 사용자의 특정 상품이 위시리스트에 존재하는지 확인
     *
     * @param userId    확인할 사용자의 ID
     * @param productId 확인할 상품의 ID
     * @return 특정 상품이 위시리스트에 존재하는지 여부
     */
    public boolean existsByUserIdAndProductId(Long userId, Long productId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM wishlist WHERE user_id = :userId AND product_id = :productId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }
}
