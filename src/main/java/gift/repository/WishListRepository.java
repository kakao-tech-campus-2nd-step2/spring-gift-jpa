package gift.repository;

import gift.domain.Menu;
import gift.domain.WishListRequest;
import gift.domain.WishListResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishListRepository {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;


    public WishListRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("wishlist")
                .usingGeneratedKeyColumns("id")
        ;
    }

    private final RowMapper<WishListResponse> wishListResponseRowMapper = new RowMapper<WishListResponse>() {
        @Override
        public WishListResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WishListResponse(
                    rs.getLong("menuId")
            );
        }
    };

    public void create(WishListRequest wishListRequest) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(wishListRequest);
        simpleJdbcInsert.execute(params);
    }


    public List<WishListResponse> findById(String jwtId) {
        String sql = "SELECT menuid FROM wishlist WHERE memberid = ?";
        try {
             List<WishListResponse> wishLists =
                    jdbcTemplate.query(sql, wishListResponseRowMapper, jwtId);
             return  wishLists;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean delete(String jwtId, Long menuId) {
        var sql = "delete from wishlist where memberid = ? and menuid = ?";
        int success = jdbcTemplate.update(sql, jwtId,menuId);
        return success > 0;
    }
}
