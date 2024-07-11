package gift.repository;

import gift.dto.user.EncryptedUpdateDTO;
import gift.dto.user.UserEncryptedDTO;
import gift.dto.user.UserInfoDTO;
import gift.exception.DuplicatedEmailException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserInfoDTO> findAll() {
        String sql = "select * from users";

        return jdbcTemplate.query(sql, (record, rowNum) -> new UserInfoDTO(
                    record.getLong("id"),
                    record.getString("email"),
                    record.getString("password")
            )
        );
    }

    public UserInfoDTO findUserByEmail(String email) {
        String sql = "select * from users where email = ?";

        UserInfoDTO userInfo = jdbcTemplate.queryForObject(sql, (userRecord, rowNum) -> new UserInfoDTO(
                userRecord.getLong("id"),
                userRecord.getString("email"),
                userRecord.getString("password")
        ), email);

        if (userInfo == null) {
            throw new NoSuchElementException("User not found");
        }

        return userInfo;
    }

    public UserInfoDTO create(UserEncryptedDTO user) {
        if (isRecordExisting(user.email())) {
            throw new DuplicatedEmailException("Email already exists");
        }

        long id = insertWithGeneratedKey(user.email(), user.encryptedPW());

        return new UserInfoDTO(id, user.email(), user.encryptedPW());
    }

    public void delete(long id) {
        if (!isRecordExisting(id)) {
            throw new NoSuchElementException("No such Account");
        }

        String sql = "delete from users where id = ?";

        jdbcTemplate.update(sql, id);
    }

    public void updatePw(EncryptedUpdateDTO encryptedUpdateDTO) {
        if (!isRecordExisting(encryptedUpdateDTO.id())) {
            throw new NoSuchElementException("No such Account");
        }

        String sql = "update users set password = ? where id = ?";

        jdbcTemplate.update(sql, encryptedUpdateDTO.encryptedPw(), encryptedUpdateDTO.id());
    }

    private boolean isRecordExisting(long id) {
        String sql = "select count(*) from users where id = ?";

        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    private boolean isRecordExisting(String email) {
        String sql = "select count(*) from users where email = ?";

        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }

    private long insertWithGeneratedKey(String email, String password) {
        String insertSql = "insert into users(email, password) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, email);
            ps.setString(2, password);

            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }
}
