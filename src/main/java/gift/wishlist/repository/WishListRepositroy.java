package gift.wishlist.repository;

import gift.wishlist.dto.WishListResponseDto;
import gift.wishlist.entity.WishList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {

    private final JdbcTemplate jdbcTemplate;

    public WishListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 제품을 위시리스트에 넣는 함수. 개수는 무조건 1
    public boolean insertWishProduct(WishList wishList) {
        long userId = wishList.getUserId();
        long productId = wishList.getProductId();
        // 이미 있는 제품을 넣은 경우, false를 반환
        if (exists(userId, productId)) {
            return false;
        }

        var sql = """
            insert into wish_products (user_id, product_id, quantity) values (?, ?, ?)
            """;

        jdbcTemplate.update(sql, wishList.getUserId(), wishList.getProductId(),
            wishList.getQuantity());

        return true;
    }

    // 개인 위시리스트에서 모든 제품을 꺼내오는 함수
    public List<WishListResponseDto> selectWishProducts(long userId) {
        var sql = """
            select wp.user_id, wp.product_id, wp.quantity, p.name, p.price, p.image
            from wish_products wp
            join products p ON wp.product_id = p.product_id
            where wp.user_id = ?;
            """;

        return jdbcTemplate.query(sql,
            (resultSet, rowNum) -> new WishListResponseDto(
                resultSet.getLong("user_id"), resultSet.getLong("product_id"),
                resultSet.getString("name"), resultSet.getInt("price"),
                resultSet.getString("image"), resultSet.getInt("quantity")
            ), userId);
    }

    // 개인 위시리스트에서 특정 제품을 꺼내오는 함수
    public WishListResponseDto selectWishProduct(long userId, long productId) {
        verifyWishProductExist(userId, productId);

        var sql = """
            select wp.user_id, wp.product_id, wp.quantity, p.name, p.price, p.image
            from wish_products wp
            join products p ON wp.product_id = p.product_id
            where wp.user_id = ? and wp.product_id = ?;
            """;

        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) -> new WishListResponseDto(
                resultSet.getLong("user_id"), resultSet.getLong("product_id"),
                resultSet.getString("name"), resultSet.getInt("price"),
                resultSet.getString("image"), resultSet.getInt("quantity")
            ), userId, productId);
    }

    // userId, productId(pk)를 갖는 제품의 양을 변경.
    public void updateWishProductQuantity(WishList wishList) {
        verifyWishProductExist(wishList.getUserId(), wishList.getProductId());
        long userId = wishList.getUserId();
        long productId = wishList.getProductId();
        int quantity = wishList.getQuantity();

        var sql = """
            update wish_products set quantity = ? where user_id = ? and product_id = ?;
            """;

        jdbcTemplate.update(sql, quantity, userId, productId);
    }

    // 제품을 제거하는 함수
    public void deleteWishProduct(long userId, long productId) {
        verifyWishProductExist(userId, productId);

        var sql = """
            delete from wish_products where user_id = ? and product_id = ?;
            """;

        jdbcTemplate.update(sql, userId, productId);
    }

    // db에서 특정 key를 갖는 로우가 있는지 확인하는 메서드
    private boolean exists(long userId, long productId) {
        var sql = """
            select user_id
            from wish_products
            where user_id = ? and product_id = ?;
            """;

        // 결과의 로우가 존재하는지 반환
        boolean isEmpty = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> resultSet.getLong("user_id"), userId, productId)
            .isEmpty();

        return !isEmpty;
    }

    // 장바구니에서 수정 및 삭제를 하려는데 모종의 이유로 없는 경우.
    private void verifyWishProductExist(long userId, long productId) {
        if (!exists(userId, productId)) {
            throw new NoSuchElementException("이미 장바구니에 없는 제품입니다.");
        }
    }
}
