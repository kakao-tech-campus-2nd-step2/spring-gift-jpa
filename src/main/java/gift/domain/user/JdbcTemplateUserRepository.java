package gift.domain.user;

import gift.domain.user.dto.UserDTO;
import gift.global.exception.BusinessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 해당 이메일 회원 DB 에 존재 여부 확인
     */
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        if (count == 1) {
            return true;
        }
        return false;
    }

    /**
     * 회원 가입
     */
    public void join(User user) {
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        int rowNum = jdbcTemplate.update(sql, user.getEmail(), user.getPassword());

        if (rowNum == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "회원 가입에 실패했습니다");
        }
    }

    /**
     * 로그인 정보 일치 여부 확인
     */
    public boolean checkUserInfo(UserDTO userDTO) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userDTO.getEmail(),
            userDTO.getPassword());
        return count != null && count > 0;
    }

    /**
     * 해당 이메일과 비밀번호를 가진 유저 정보 반환
     */
    public User findByEmailAndPassword(UserDTO userDTO) {
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            User user = jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(User.class), userDTO.getEmail(),
                userDTO.getPassword());

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "로그인 입력 정보가 올바르지 않습니다.");
        }
    }
}
