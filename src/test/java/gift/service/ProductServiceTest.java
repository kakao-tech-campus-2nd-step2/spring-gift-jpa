package gift.service;

import gift.product.service.AdminProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AdminProductService adminProductService;

    @Test
    public void testTableCreate() {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'product'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertThat(count).isNotNull();
        assertThat(count).isGreaterThan(0);

    }
}