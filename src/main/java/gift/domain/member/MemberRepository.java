package gift.domain.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member registerMember(Member member) {
        String sql = "INSERT INTO member(email,name,password,role) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getName());
            ps.setString(3, member.getPassword());
            ps.setInt(4, member.getRole());
            return ps;
        }, keyHolder);

        member.setId(keyHolder.getKey().longValue());

        return member;
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member where email = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            int role = rs.getInt("role");
            return new Member(id, email, name, password, role);
        }, email);
    }

    public boolean isNotValidMember(String email) {
        String sql = "SELECT * FROM member WHERE email=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, email).isEmpty();
    }

    public boolean isNotValidMemberById(Long id) {
        String sql = "SELECT * FROM member WHERE id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public boolean isAlreadyExist(String email) {
        String sql = "SELECT * FROM member WHERE email=?";
        return !jdbcTemplate.query(sql, (rs, rowNum) -> 0, email).isEmpty();
    }
}
