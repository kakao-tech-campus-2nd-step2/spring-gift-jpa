package gift.repository.wish;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class WishJdbcRepository implements WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Wish> findWishByMemberEmail(String email) {
        String sql = "SELECT w.id as wish_id, w.count as wish_count, " +
                "p.id as product_id, p.name as product_name, p.price as product_price, p.imageUrl as product_imageUrl, " +
                "m.id as member_id, m.email as member_email, m.password as member_password " +
                "FROM wish w " +
                "JOIN product p ON w.product_id = p.id " +
                "JOIN member m ON w.email = m.email " +
                "WHERE w.email = ?";

        List<Wish> wishes = jdbcTemplate.query(sql, new Object[]{email}, new RowMapper<Wish>() {
            @Override
            public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wish wish = new Wish();
                wish.setId(rs.getLong("wish_id"));
                wish.setCount(rs.getInt("wish_count"));

                Product product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getInt("product_price"));
                product.setImageUrl(rs.getString("product_imageUrl"));
                wish.setProduct(product);

                Member member = new Member();
                member.setId(rs.getLong("member_id"));
                member.setEmail(rs.getString("member_email"));
                member.setPassword(rs.getString("member_password"));
                wish.setMember(member);

                return wish;
            }
        });

        return wishes;
    }

    @Override
    public Optional<Wish> findById(Long id) {
        String sql = "SELECT w.id as wish_id, w.count as wish_count, " +
                "p.id as product_id, p.name as product_name, p.price as product_price, p.imageUrl as product_imageUrl, " +
                "m.id as member_id, m.email as member_email, m.password as member_password " +
                "FROM wish w " +
                "JOIN product p ON w.product_id = p.id " +
                "JOIN member m ON w.email = m.email " +
                "WHERE w.id = ?";

        List<Wish> wishes = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Wish>() {
            @Override
            public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wish wish = new Wish();
                wish.setId(rs.getLong("wish_id"));
                wish.setCount(rs.getInt("wish_count"));

                Product product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getInt("product_price"));
                product.setImageUrl(rs.getString("product_imageUrl"));
                wish.setProduct(product);

                Member member = new Member();
                member.setId(rs.getLong("member_id"));
                member.setEmail(rs.getString("member_email"));
                member.setPassword(rs.getString("member_password"));
                wish.setMember(member);

                return wish;
            }
        });

        if(wishes.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(wishes.getFirst());
    }

    @Override
    public Integer findWishCountByWishIdAndMemberEmail(Long wishId, String email) {
        String sql = "SELECT COUNT(*) FROM wish WHERE id = ? AND email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{wishId, email}, Integer.class);
    }

    @Override
    public Long wishSave(Long productId, String email, int count) {
        String sql = "INSERT INTO wish (product_id, email, count) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productId, email, count);
        return productId;
    }

    @Override
    public Long updateWish(Long wishId, int count) {
        String sql = "UPDATE wish SET count = ? WHERE id = ?";
        int updatedRow = jdbcTemplate.update(sql, count, wishId);
        return (long) updatedRow;
    }

    @Override
    public Long deleteWish(Long wishId) {
        String sql = "DELETE FROM wish WHERE id = ?";
        int deletedRow = jdbcTemplate.update(sql, wishId);
        return (long) deletedRow;
    }
}
