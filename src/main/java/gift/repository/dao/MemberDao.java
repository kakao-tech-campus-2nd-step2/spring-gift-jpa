package gift.repository.dao;

import gift.common.enums.Role;
import gift.controller.dto.request.MemberRequest;
import gift.controller.dto.request.SignInRequest;
import gift.model.Member;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {
    private final JdbcClient jdbcClient;

    public MemberDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void save(String email, String password) {
        save(email, password, Role.USER);
    }

    public void save(String email, String password, Role role) {
        var sql = "insert into member (email, password, role) values (?, ?, ?)";
        jdbcClient.sql(sql)
                .params(email, password, role.toString())
                .update();
    }

    public List<Member> findAll() {
        var sql = "select * from member";
        return jdbcClient.sql(sql)
                .query(Member.class)
                .list();
    }

    public Optional<Member> findById(Long id) {
        var sql = "select * from member where id = ?";
        return jdbcClient.sql(sql)
                .params(id)
                .query(Member.class)
                .optional();
    }

    public Optional<Member> findByEmailAndPassword(SignInRequest request) {
        var sql = "select * from member where email = ? and password = ?";
        return jdbcClient.sql(sql)
                .params(request.email(), request.password())
                .query(Member.class)
                .optional();
    }

    public boolean existsByEmail(String email) {
        var sql = "select count(*) from member where email = ?";
        int count = jdbcClient.sql(sql)
                .params(email)
                .query(Integer.class)
                .single();
        return count > 0;
    }

    public void updatedById(Long id, MemberRequest request) {
        var sql = "update member set email = ?, password = ?, role = ? where id = ?";
        jdbcClient.sql(sql)
                .params(request.email(), request.password(), request.role(), id)
                .update();
    }

    public void deleteById(Long id) {
        var sql = "delete from member where id = ?";
        jdbcClient.sql(sql)
                .params(id)
                .update();
    }
}
