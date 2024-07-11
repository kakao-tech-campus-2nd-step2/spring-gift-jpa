//package gift.dao;
//
//import gift.model.Wish;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class WishDao {
//    private final JdbcTemplate jdbcTemplate;
//
//    public WishDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public RowMapper<Wish> WishlistRowMapper() {
//        return (resultSet, rowNum) -> new Wish(
//                resultSet.getLong("id"),
//                resultSet.getLong("userid"),
//                resultSet.getLong("productid")
//        );
//    }
//
//    public List<Wish> selectAllWishlist(Long id){
//        var sql = "select * from wishlist where userid = ?";
//        List<Wish> list = new ArrayList<>();
//        return jdbcTemplate.query(sql, WishlistRowMapper(), id);
//    }
//
//    public void insertWishlist(Wish wish){
//        var sql = "insert into wishlist (userid, productid) values (?, ?)";
//        jdbcTemplate.update(sql, wish.getMemberId(), wish.getProductId());
//    }
//
//    public void deleteWishlist(Long id){
//        var sql = "delete from wishlist where id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//}
