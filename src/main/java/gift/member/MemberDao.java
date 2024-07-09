package gift.member;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class MemberDao {
    @Autowired
    private JdbcClient jdbcClient;
    private JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertMember(Member member) {
        String sql = """
            INSERT INTO member (id, name, email, password, role) 
            VALUES (?,?,?,?,?)
            """;
//        jdbcClient.sql(sql)
//            .param(member.getId())
//            .param(member.getName())
//            .param(member.getEmail())
//            .param(member.getPassword())
//            .param(member.isRole())
//            .update();
        jdbcTemplate.update(sql,
            member.getId(),
            member.getName(),
            member.getEmail(),
            member.getPassword(),
            member.isRole());
    }

    public Optional<Member> findMember(Member member) {
        String sql = """
            SELECT *
            FROM member 
            WHERE email = ? AND password = ?
            """;

        List<Member> mem = jdbcTemplate.query(
            sql,
            (rs,rowNum) -> new Member(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("role")
            ),
            member.getEmail(),
            member.getPassword()
        );

        if (!mem.isEmpty()) {
            return Optional.of(mem.get(0));
        } else {
            return Optional.empty();
        }

//        return jdbcClient.sql(sql)
//            .param(member.getEmail())
//            .param(member.getPassword())
//            .query(Member.class).optional();
    }

    public Member findMemberById(String userEmail) {
        String sql = """
            SELECT * 
            FROM member 
            WHERE email = ?
            """;

        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new Member(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("role")
            ),
            userEmail
        );

//        //return jdbcClient.sql(sql)
//            .param(userEmail)
//            .query(Member.class).single();
    }

//    public List<Member> findAllMember() {
//        String sql = """
//            SELECT
//              email,
//              password
//            FROM member
//            """;
//        List<Member> members = jdbcClient.sql(sql).query(Member.class).list();
//        return members;
//    }
}

