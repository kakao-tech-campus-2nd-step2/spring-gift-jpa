package gift.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProduct;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, DataSource dataSource){
        this.jdbcTemplate = jdbcTemplate;
        this.insertProduct = new SimpleJdbcInsert(dataSource)
            .withTableName("site_user")
            .usingGeneratedKeyColumns("id");
    }

    public List<SiteUser> findAll(){
        return jdbcTemplate.query("SELECT * FROM site_user", new BeanPropertyRowMapper<>(SiteUser.class));
    }

    public SiteUser findById(Long id){
        return jdbcTemplate.queryForObject("SELECT * FROM site_user WHERE id = ?", new BeanPropertyRowMapper<>(
            SiteUser.class), id);
    }

    public SiteUser findByEamilAndPassword(String email, String password){
        return jdbcTemplate.queryForObject("SELECT * FROM site_user WHERE email = ? and password = ?", new BeanPropertyRowMapper<>(SiteUser.class), email, password);
    }

    public long save(SiteUser user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        return insertProduct.executeAndReturnKey(parameters).longValue();
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM site_user WHERE id = ?", id);
    }
}
