package gift.user.infrastructure.persistence;

import gift.core.domain.user.User;
import gift.core.domain.user.UserAccount;
import gift.core.domain.user.UserAccountRepository;
import gift.core.domain.user.UserRepository;
import gift.core.domain.user.exception.UserAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserAccountRepository userAccountRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(
            JdbcTemplate jdbcTemplate,
            UserAccountRepository userAccountRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                MERGE INTO `user` AS target
                USING (VALUES (?, ?)) AS source (id, name)
                ON target.id = source.id
                WHEN MATCHED THEN
                UPDATE SET target.name = source.name
                WHEN NOT MATCHED THEN
                INSERT (name) VALUES (source.name);
        """;
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, user.id());
            statement.setString(2, user.name());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("Failed to save user");
        }
        userAccountRepository.save(keyHolder.getKey().longValue(), user.account());
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM `user` WHERE `id` = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        return result != null && result;
    }

    @Override
    public User findById(Long id) {
        UserAccount account = userAccountRepository.findByUserId(id);
        if (account == null) {
            throw new UserAccountNotFoundException();
        }
        String sql = "SELECT * FROM `user` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                account
        ), id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM `user` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
        userAccountRepository.delete(id);
    }
}
