package gift.repositories;


import gift.Wish;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    wish 추가
    public void insertWish(Long memberId, Long productId){
        jdbcTemplate.execute("INSERT INTO wishes (memberId, productId) VALUES("
            + "'" + memberId + "', "
            + productId + ");"
        );
    }

//    memberId로 Wish list 가져오기 //맞는 문법인지 확인!!!!!!!!!!!
    public List<Wish> findWishListById(Long memberId) {
        List<Wish> queried = jdbcTemplate.query("SELECT "
            + "wishes.memberId, wishes.productId, products.name "
            + "FROM wishes "
            + "INNER JOIN products "
            + "ON wishes.productId = products.id "
            + "WHERE wishes.memberId = " + memberId,
            (resultSet, rowNum) ->
            new Wish(
                resultSet.getLong("wishes.memberId"),
                resultSet.getLong("wishes.productId"),
                resultSet.getString("products.name")
            )
        );
        return queried;
    }

//    memberId로 Wish 삭제
    public void deleteWish(Long memberId, Long productId){
        jdbcTemplate.execute("DELETE FROM wishes WHERE memberId='"
            + memberId + "' , productId= '"
            + productId +";");
    }
}
