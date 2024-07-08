package gift.service;

import gift.exception.UserNotFoundException;
import gift.model.Member;
import gift.model.MemberDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member createMember(MemberDTO memberDTO) {
        String sql = "INSERT INTO members (password, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberDTO.password(), memberDTO.email());
        return getMemberByEmail(memberDTO.email());
    }

    public Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), email);
    }

    public Member getMemberByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM members WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class),
                email, password);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("해당 이메일과 비밀번호를 갖는 사용자를 찾을 수 없습니다.");
        }
    }

}
