package gift.product.dao;

import gift.product.model.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMemberTable() {
        System.out.println("[MemberDao] createMemberTable()");
        var sql = """
            create table member_list (
              email varchar(255) not null unique,
              password varchar(255) not null,
              primary key (email)
            )
            """;
        jdbcTemplate.execute(sql);
    }

    public void signUp(Member member) {
        System.out.println("[MemberDao] signUp()");
        var sql = "insert into member_list (email, password) values (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    public boolean isExistsMember(String email) {
        System.out.println("[MemberDao] isExistsMember()");
        String sql = "select count(*) from member_list where email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean validateMember(String email, String password) {
        System.out.println("[MemberDao] validateMember()");
        String sql = "select count(*) from member_list where email = ? and password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        return count != null && count > 0;
    }

    private RowMapper<Member> memberRowMapper = new RowMapper<Member>() {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Member(
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("role")
            );
        }
    };

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, new Object[]{email}, memberRowMapper);
            return Optional.ofNullable(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
