package gift.database;

import gift.model.Member;
import gift.model.MemberRole;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemeberRepository {

    private final JdbcTemplate template;

    public JdbcMemeberRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        createTable();
    }

    private void createTable() {
        template.update(
            "create table if not exists member("
                + "id long primary key auto_increment, "
                + "email varchar(255) unique not null, "
                + "password varchar(255) not null, "
                + "role varchar(255) not null)");
    }


    public void create(String email, String password, String role) {
        String sql = "insert into member (email, password,role) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);
            return ps;
        }, keyHolder);
    }

    public void update(long id, Member member) {
        String sql = "update member set email=?, password=?, role=? where id=?";
        template.update(sql, member.getEmail(), member.getPassword(), member.getRole().toString(),
            id);
    }

    public void delete(long id) {
        String sql = "delete from member where id = ?";
        template.update(sql, id);
    }

    public Member findById(long id) {
        String sql = "select * from member where id = ?";
        return template.queryForObject(sql, memberRowMapper(), id);
    }

    public Member findByEmail(String email) {
        String sql = "select * from member where email = ?";
        return template.queryForObject(sql, memberRowMapper(), email);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            MemberRole.valueOf(rs.getString("role")));
    }


}
