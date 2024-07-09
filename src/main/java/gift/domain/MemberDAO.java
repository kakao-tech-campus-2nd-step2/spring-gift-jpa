package gift.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
    private final JdbcTemplate jdbcTemplate;

    public MemberDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create() {
        var sql = """
                create table member (
                    email varchar(255),
                    password varchar(255),
                    token varchar(255) unique primary key
                )
                """;
        jdbcTemplate.execute(sql);
    }


    public String insert(Member member, String token) {
        var sql = "INSERT INTO member (email, password, token) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.email(), member.password(), token);
        return token;
    }

    public String selectTokenbyMember(Member member){
        var sql = "SELECT token FROM member WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, String.class, member.email(), member.password());
    }

    public String selectTokenbyToken(String token){
        var sql = "SELECT token FROM member WHERE token = ? limit 1";
        return jdbcTemplate.queryForObject(sql, String.class, token);
    }
}
