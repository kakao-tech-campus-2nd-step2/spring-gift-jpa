package gift.repository;

import gift.dto.Member;
import gift.dto.request.LoginInfoRequest;
import gift.dto.request.MemberRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initialize() {
        createMemberTable();
        registerMember(new MemberRequest("test@test.com", "1234"));
        registerMember(new MemberRequest("test2@test.com", "1234"));
    }

    private void createMemberTable() {
        var sql = """
                create table member (
                  id bigint AUTO_INCREMENT,
                  email varchar(255) unique,
                  password varchar(255),
                  primary key (id)
                )
                """;
        jdbcTemplate.execute(sql);
    }

    public Long registerMember(MemberRequest member) {
        String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            return ps;
        }, keyHolder);
        if (rowsAffected > 0) {
            return keyHolder.getKey().longValue();
        }
        return -1L;
    }

    public boolean hasDuplicatedEmail(String email) {
        String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public Long getMemberIdByEmailAndPassword(LoginInfoRequest loginInfo) {
        String sql = "select id, email, password from member where email = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), loginInfo.getEmail(), loginInfo.getPassword()).getId();
    }

    private static class UserRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        }
    }

}
