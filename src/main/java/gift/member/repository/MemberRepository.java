package gift.member.repository;

import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.Password;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final JdbcClient jdbcClient;

    public MemberRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Member> findAll() {
        String sql = "select * from members";
        return jdbcClient.sql(sql)
                .query(Member.class)
                .list();
    }

    public void save(Member member) {
        Assert.notNull(member, "Member must not be null");
        if (member.checkNew()) {
            String sql = "INSERT INTO members (member_type, email, password, nickname) VALUES (?, ?, ?, ?)";
            jdbcClient.sql(sql)
                    .param(member.getMemberType().getValue())
                    .param(member.getEmail().getValue())
                    .param(member.getPassword().getValue())
                    .param(member.getNickName().getValue())
                    .update();

        }
        if (!member.checkNew()) {
            String sql = "UPDATE members SET email = ?, password = ?, nickname = ? WHERE id = ?";
            jdbcClient.sql(sql)
                    .param(member.getEmail().getValue())
                    .param(member.getPassword().getValue())
                    .param(member.getNickName().getValue())
                    .param(member.getId())
                    .update();
        }
    }

    public Optional<Member> findByEmailValueAndPasswordValue(Email email, Password password) {
        Assert.notNull(email, "Email must not be null");
        Assert.notNull(password, "Password must not be null");

        String sql = "SELECT * FROM members WHERE email = ? AND password = ?";
        return jdbcClient.sql(sql)
                .param(email.getValue())
                .param(password.getValue())
                .query(Member.class)
                .optional();
    }

    public Optional<Member> findById(Long id) {
        String sql = "select * from members where id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(Member.class)
                .optional();
    }

    public void deleteById(Long id) {
        String sql = "delete from members where id = ?";
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
}
