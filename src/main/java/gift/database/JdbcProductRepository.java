package gift.database;


import gift.model.Product;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository {
    private final JdbcTemplate template;

    public JdbcProductRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        createTable(); //초기 테이블 생성
    }

    private void createTable() {
        template.update("create table if not exists product("
            + "id long primary key auto_increment, "
            + "name varchar(255), "
            + "price int,"
            + "imageUrl varchar(255))");
    }


    public Product create(String name, int price, String imageUrl) {
        String sql = "insert into product (name, price, imageUrl) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setString(3, imageUrl);
            return ps;
        },keyHolder);
        long key = keyHolder.getKey().longValue();

        return new Product(key, name, price, imageUrl);
    }

    //기존 상품 수정
    public void update(long id, Product product) {
        String sql = "update product set name = ?, price = ?, imageUrl = ? where id = ?";
        template.update(sql,product.getName(),product.getPrice(),product.getImageUrl(),id);
    }

    //상품 단일 조회
    public Product findById(long id) {
        String sql = "select * from product where id = ?";
        return template.queryForObject(sql,productRowMapper(),id);
    }

    //상품 전체 조회
    public List<Product> findAll() {
        String sql = "select * from product";
        return template.query(sql,productRowMapper());
    }

    //상품 삭제
    public void delete(long id) {
        String sql = "delete from product where id = ?";
        template.update(sql,id);
    }


    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> new Product(rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("imageUrl"));

    }

}
