package gift.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class WishProductDummyDataProvider {

    private final JdbcTemplate jdbcTemplate;

    private static final Long TARGET_MEMBER_ID = 1L;

    public WishProductDummyDataProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void run(int quantity) {
        doRun(quantity);
    }

    private void doRun(int quantity) {
        String sql = "insert into wish_product (member_id, product_id, quantity) values (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, getBatchPreparedStatementSetter(quantity));
    }

    private static BatchPreparedStatementSetter getBatchPreparedStatementSetter(int quantity) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, TARGET_MEMBER_ID);
                ps.setLong(2, i + 1);
                ps.setInt(3, i + 1);
            }

            @Override
            public int getBatchSize() {
                return quantity;
            }
        };
    }
}