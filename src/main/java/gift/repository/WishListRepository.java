package gift.repository;

import gift.dto.WishDTO;
import gift.entity.WishList;
import gift.exception.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean isExistWishList(WishList wishList) {
        String sql = "select count(*) from wishlist where user_id=? and product_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, wishList.getUser_id(), wishList.getProduct_id());
        return count > 0;
    }

    public void save(WishList wishList){
        var sql = "insert into wishlist(user_id,product_id) values(?,?)";
        jdbcTemplate.update(sql,wishList.getUser_id(),wishList.getProduct_id());
    }

    public List<WishDTO.wishListProduct> getWishList(int tokenUserId) {
        var sql = "select product.name, product.price, product.imageUrl from wishlist inner join product on wishlist.product_id = product.id where user_id = ?";
        List<WishDTO.wishListProduct> wishList = jdbcTemplate.query(sql,new Object[]{tokenUserId},(rs, rowNum) -> new WishDTO.wishListProduct(
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")
        ));
        return wishList;
    }

    public void deleteWishList(int tokenUserId, int productId) {
        if(!isExistWishList(new WishList(tokenUserId,productId)))
            throw new NotFoundException("wishList에 포함되어있지않음");
        var sql = "delete from wishList where user_id = ? and product_id = ?";
        jdbcTemplate.update(sql,tokenUserId,productId);
    }
}
