package gift.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUser(Member member){
        String sql = "insert into users (email,password) values (?,?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    public Optional<Member> findByEmail(String email){
        String sql = "select * from users where email = ?";
        List<Member> members = jdbcTemplate.query(sql, userRowMapper(), email);
        if(members.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(members.get(0));
    }

    private RowMapper<Member> userRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        };
    }

    public boolean existUserByEmail(String email){
        String sql = "select * from users where email = ?";
        List<Member> members = jdbcTemplate.query(sql, userRowMapper(), email);
        return members.size()>0;

    }
}
