package gift.repository.member;

import gift.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJdbcRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public MemberJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member memberSave(Member member) {
        String sql = "INSERT INTO member(email,password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
        return member;
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        String sql = "SELECT id, email, password FROM member WHERE id = ?";
        List<Member> member = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        ));

        if (member.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(member.getFirst());
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        String sql = "SELECT id, email, password FROM member WHERE email = ?";
        List<Member> member = jdbcTemplate.query(sql, new Object[]{email}, (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        ));

        if (member.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(member.getFirst());
    }

    @Override
    public Optional<Member> findMemberByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, email, password FROM member WHERE email = ? and password = ?";
        List<Member> member = jdbcTemplate.query(sql, new Object[]{email, password}, (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        ));

        if (member.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(member.getFirst());
    }

    @Override
    public List<Member> findAllMember() {
        String sql = "SELECT id, email, password FROM member";
        List<Member> members = jdbcTemplate.query(sql, (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        ));

        return members;
    }
}
