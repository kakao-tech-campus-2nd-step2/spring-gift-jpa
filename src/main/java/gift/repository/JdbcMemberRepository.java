package gift.repository;

import gift.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class JdbcMemberRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Long save(Member member) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO member (email, password) VALUES (?, ?)";

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, member.getEmail());
      ps.setString(2, member.getPassword());
      return ps;
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  public Optional<Member> findByEmail(String email) {
    String sql = "SELECT * FROM member WHERE email = ?";
    try {
      Member member = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
        Member m = new Member();
        m.setId(rs.getLong("id"));
        m.setEmail(rs.getString("email"));
        m.setPassword(rs.getString("password"));
        return m;
      });
      return Optional.ofNullable(member);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
  public Optional<Member> findById(Long id) {
    String sql = "SELECT * FROM member WHERE id = ?";
    try {
      Member member = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
        Member m = new Member();
        m.setId(rs.getLong("id"));
        m.setEmail(rs.getString("email"));
        m.setPassword(rs.getString("password"));
        return m;
      });
      return Optional.ofNullable(member);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
