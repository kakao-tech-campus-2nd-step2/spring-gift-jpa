package gift.Repository;

import gift.Model.Product;
import gift.Model.RequestWishListDTO;
import gift.Model.ResponseWishListDTO;
import gift.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbctemplate;


    public WishListRepository(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    public void insertWishList(String email, RequestWishListDTO requestWishListDTO) {
        var sql = "insert into WishLists (email, productId, count) values (?,?,?)";
        jdbctemplate.update(sql, email, requestWishListDTO.getProductId(), requestWishListDTO.getCount());
    }

    public List<ResponseWishListDTO> selectWishList(String email) {
        var sql = "select name, count from wishLists INNER JOIN products on wishlists.productId = products.id where email= ?";
        return jdbctemplate.query(sql, new Object[]{email}, responseWishListDTORowMapper());
    }

    public List<ResponseWishListDTO> updateWishList(String email, RequestWishListDTO requestWishListDTO) {
        var sql = "UPDATE wishLists SET count = ? WHERE email = ? AND productId = ?";
        jdbctemplate.update(sql, requestWishListDTO.getCount(), email, requestWishListDTO.getProductId());
        return selectWishList(email);
    }

    public List<ResponseWishListDTO> deleteWishList(String email, RequestWishListDTO requestWishListDTO) {
        var sql = "DELETE FROM wishLists WHERE email = ? AND productId = ?";
        jdbctemplate.update(sql, email, requestWishListDTO.getProductId());
        return selectWishList(email);
    }

    private RowMapper<ResponseWishListDTO> responseWishListDTORowMapper() {
        return (rs, rowNum) -> new ResponseWishListDTO(
                rs.getString("name"),
                rs.getInt("count")
        );
    }

}

