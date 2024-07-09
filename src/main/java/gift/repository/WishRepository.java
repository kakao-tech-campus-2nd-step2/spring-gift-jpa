package gift.repository;

import gift.dto.WishDto;
import gift.entity.Product;
import gift.entity.Wish;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public WishRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("wishes").usingGeneratedKeyColumns("id");
    }

    public Map<String, Object> save(Long productId, String token) {

        Map<String, Object> parameters = Map.of("product_id", productId, "token", token);

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        long id = newId.longValue();

        parameters.put("id", id);

        return parameters;

    }

    public Map<Wish, Product> getAll(String token) {
        var sql = """
                select w.id, p.id, w.token,p.name,p.price,p.url
                from (wishes as w 
                join products as p
                where w.productId=p.id) as wish_product
                where w.token =? 
                """;
        return jdbcTemplate.query(
                        sql,
                        (resultSet, rowNum) -> {
                            WishDto wishDto = new WishDto(resultSet.getLong("w.id"), resultSet.getLong("p.id"), resultSet.getString("w.token"));
                            Product product = new Product(resultSet.getInt("p.id"), resultSet.getString("p.name"), resultSet.getInt("p.price"), resultSet.getString("p.url"));
                            return new AbstractMap.SimpleEntry<>(wishDto, product);
                        },
                        token).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public ResponseEntity<Map<String, Object>> delete(Long id, String token) {

        WishDto wishDto = findOneById(id);

        if (!(token.equals(wishDto.getToken()))) {
            Map<String, Object> parameters = Map.of("메세지", "삭제할 권한이 없습니다.");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(parameters);
        }

        var sql = "delete from wish where id = ?";

        Map<String, Object> parameters = Map.of("wish", wishDto);

        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().header("Authorization", "Basic" + wishDto.getToken()).contentType(MediaType.APPLICATION_JSON).body(parameters);
    }

    public WishDto findOneById(Long id) {
        var sql = "select id,productId,token from wish where id= ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new WishDto(id, resultSet.getLong("productId"), resultSet.getString("token")), id);
    }
}
