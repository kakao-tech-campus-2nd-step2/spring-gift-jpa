package gift.user.infrastructure.persistence;

import gift.core.domain.user.UserAccount;
import gift.core.domain.user.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Long userId, UserAccount userAccount) {
        String sql = """
                MERGE INTO `user_account` AS target
                USING (VALUES (?, ?, ?)) AS source (`user_id`, `principal`, `credentials`)
                ON target.`user_id` = source.`user_id` OR target.`principal` = source.`principal`
                WHEN MATCHED THEN
                UPDATE SET target.principal = source.`principal`, target.`credentials` = source.`credentials`
                WHEN NOT MATCHED THEN
                INSERT (`user_id`, `principal`, `credentials`) VALUES (source.`user_id`, source.`principal`, source.`credentials`);
        """;
        jdbcTemplate.update(sql, userId, userAccount.principal(), userAccount.credentials());
    }

    @Override
    public boolean exists(Long userId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM `user_account` WHERE `user_id` = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId);
        return result != null && result;
    }

    @Override
    public boolean existsByPrincipal(String principal) {
        String sql = "SELECT EXISTS(SELECT 1 FROM `user_account` WHERE `principal` = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, principal);
        return result != null && result;
    }

    @Override
    public UserAccount findByUserId(Long userId) {
        String sql = "SELECT * FROM `user_account` WHERE `user_id` = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserAccount(
                rs.getString("principal"),
                rs.getString("credentials")
        ), userId);
    }

    @Override
    public UserAccount findByPrincipal(String principal) {
        String sql = "SELECT * FROM `user_account` WHERE `principal` = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserAccount(
                rs.getString("principal"),
                rs.getString("credentials")
        ), principal);
    }

    @Override
    public Long findUserIdByPrincipal(String principal) {
        String sql = "SELECT `user_id` FROM `user_account` WHERE `principal` = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, principal);
    }

    @Override
    public void delete(Long userId) {
        String sql = "DELETE FROM `user_account` WHERE `user_id` = ?";
        jdbcTemplate.update(sql, userId);
    }
}
