package gift.wishlist.repository;

import gift.wishlist.WishList;
import gift.wishlist.exception.WishListCreateException;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private final JdbcClient jdbcClient;

    public WishListRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<WishList> findWishListsByMemberId(Long memberId) {
        var sql = """
                select id, member_id, product_id, quantity
                from wish_list
                where member_id = ?
                """;

        return jdbcClient.sql(sql)
                .param(memberId)
                .query(WishList.class)
                .list();
    }

    public void addWishList(Long memberId, Long productId, Integer quantity) {
        var sql = """
                insert into wish_list (member_id, product_id, quantity)
                values (?, ?, ?)
                """;

        try {
            jdbcClient.sql(sql)
                    .param(memberId)
                    .param(productId)
                    .param(quantity)
                    .update();
        } catch (Exception e) {
            throw WishListCreateException.EXCEPTION;
        }
    }

    public void updateWishListById(Long wishListId, Integer quantity) {
        var sql = """
                update wish_list
                set quantity = ?
                where id = ?
                """;

        jdbcClient.sql(sql)
                .param(quantity)
                .param(wishListId)
                .update();
    }

    public void deleteWishListById(Long wishListId) {
        var sql = """
                delete from wish_list
                where id = ?
                """;

        jdbcClient.sql(sql)
                .param(wishListId)
                .update();
    }

    public boolean isWishListExistByMemberIdAndWishListId(Long memberId, Long wishListId) {
        var sql = """
                select count(*)
                from wish_list
                where member_id = ? and id = ?
                """;

        return jdbcClient.sql(sql)
                .param(memberId)
                .param(wishListId)
                .query(Long.class)
                .single() > 0L;
    }

    public boolean isWishListExistByMemberIdAndProductId(Long memberId, Long aLong) {
        var sql = """
                select count(*)
                from wish_list
                where member_id = ? and product_id = ?
                """;

        return jdbcClient.sql(sql)
                .param(memberId)
                .param(aLong)
                .query(Long.class)
                .single() > 0L;
    }

    // 기존의 수량에 요청한 수량을 더한다.
    public void addQuantityByMemberIdAndProductId(Long memberId, Long productId, Integer quantity) {
        var sql = """
                update wish_list
                set quantity = quantity + ?
                where member_id = ? and product_id = ?
                """;

        jdbcClient.sql(sql)
                .param(quantity)
                .param(memberId)
                .param(productId)
                .update();
    }
}
