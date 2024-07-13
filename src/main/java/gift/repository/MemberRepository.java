package gift.repository;

import gift.domain.Member;
import gift.domain.vo.Email;
import gift.domain.vo.Password;
import gift.repository.mapper.MemberRowMapper;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Member member) {
        String sql = "insert into member (email, password, name) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"member_id"});
            ps.setString(1, member.getEmail().getValue());
            ps.setString(2, member.getPassword().getValue());
            ps.setString(3, member.getName());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Member> findById(Long id) {
        String sql = "select member_id, email, password, name from member where member_id = ?";
        Member findMember = jdbcTemplate.queryForObject(sql, getMemberRowMapper(), id);
        return Optional.ofNullable(findMember);
    }

    public Optional<Member> findByEmail(Email email) {
        String sql = "select member_id, email, password, name from member where email = ?";

        try {
            Member findMember = jdbcTemplate.queryForObject(sql, getMemberRowMapper(), email.getValue());
            return Optional.ofNullable(findMember);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Member> getMemberRowMapper() {
        return new MemberRowMapper();
    }
}
