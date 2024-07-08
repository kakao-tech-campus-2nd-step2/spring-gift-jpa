package gift.service;

import gift.exception.RepositoryException;
import gift.model.WishList;
import gift.model.WishListDTO;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;


    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createWishList(WishListDTO wishListDTO) {
        String sql = "INSERT INTO wishlist (email, memberId, productName, quantity) VALUES (?, ?, ?, ?)";
        if (jdbcTemplate.update(sql, wishListDTO.email(), wishListDTO.memberId(), wishListDTO.productName(),
            wishListDTO.quantity()) < 1) {
            throw new RepositoryException("해당 상품을 위시 리스트에 등록할 수 없습니다.");
        }

    }

    public List<WishList> getAllWishList() {
        String sql = "SELECT * FROM wishlist";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WishList.class));
    }

    public List<WishList> getWishListById(long id) {
        String sql = "SELECT * FROM wishlist WHERE memberId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WishList.class), id);
    }

    public void updateWishListQuantity(WishListDTO wishListDTO) {
        String sql = "UPDATE wishlist SET quantity = ?";
        if (jdbcTemplate.update(sql, wishListDTO.quantity()) <= 0) {
            throw new RepositoryException("상품 개수를 업데이트 하지 못햇습니다.");
        }
    }

    public void deleteWishList(String memberId, String productName) {
        String sql = "DELETE FROM wishlist WHERE memberId = ? AND productName = ?";
        if (jdbcTemplate.update(sql, memberId, productName) <= 0) {
            throw new RepositoryException("해당 상품을 위시 리스트에서 찾지 못했습니다.");
        }
    }
}
