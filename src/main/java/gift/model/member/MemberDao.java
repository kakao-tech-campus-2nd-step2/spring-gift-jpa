package gift.model.member;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {

    private static final String USER_INSERT = "INSERT INTO member (email, password, name, role) VALUES (?, ?, ?,?)";
    private static final String USER_SELECT_BY_ID = "SELECT id, email, password, name, role FROM member WHERE id = ?";
    private static final String USER_SELECT_BY_EMAIL = "SELECT id, email, password, name, role FROM member WHERE email = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> userRowMapper = new MemberRowMapper();

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Member member) {
        jdbcTemplate.update(USER_INSERT, member.getEmail(), member.getPassword(), member.getName(),
            Role.USER.toString());
    }

    public Optional<Member> findById(Long id) {
        try {
            Member member = jdbcTemplate.queryForObject(USER_SELECT_BY_ID,
                userRowMapper, id);
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByEmail(String email) {
        try {
            Member member = jdbcTemplate.queryForObject(USER_SELECT_BY_EMAIL,
                userRowMapper, email);
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
