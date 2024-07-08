package gift.dao;

import gift.domain.ProductWithQuantity;
import gift.domain.Wishlist;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDao {

    private final JdbcTemplate jdbcTemplate;

    public WishlistDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @PostConstruct
    public void createWishlistTable(){
        String sql= """
            CREATE TABLE Wishlist(
            id bigint AUTO_INCREMENT,
            email varchar(255),
            PRIMARY KEY(id)
            );
            """;
        jdbcTemplate.execute(sql);

        sql = """
            CREATE TABLE ProductWithQuantity(
            id bigint AUTO_INCREMENT,
            product_id bigint,
            quantity int,
            wishlist_id bigint,
            PRIMARY KEY(id),
            FOREIGN KEY(wishlist_id) REFERENCES Wishlist(id)
            );
            """;
        jdbcTemplate.execute(sql);
    }

    public void addWishlist(String email){
        String sql="INSERT INTO Wishlist(email) values(?)";
        jdbcTemplate.update(sql,email);
    }

    public Long getWishlistId(String email) {
        try{
            String sql = "SELECT id FROM Wishlist WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, Long.class,email);}
        catch(EmptyResultDataAccessException e){
            return 0L;
        }
    }

    public List<Wishlist> findAllWishlist(String email){
        String sql = "SELECT product_id,quantity FROM Wishlist,ProductWithQuantity WHERE Wishlist.id=wishlist_id and email=?";
        return jdbcTemplate.query(sql,(resultSet,rowNum)-> {
            return new Wishlist(email, new ProductWithQuantity(
                resultSet.getLong("product_id"),
                resultSet.getInt("quantity")
            )
            );
        },email);

    }
    public void deleteByProductId(Long id, String email){
        String sql = "DELETE FROM ProductWithQuantity WHERE id IN(SELECT ProductWithQuantity.id FROM Wishlist, ProductWithQuantity WHERE email=? and Wishlist.id=wishlist_id and product_id=?)";
        jdbcTemplate.update(sql,email,id);

    }



}
