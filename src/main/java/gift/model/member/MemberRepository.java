package gift.model.member;

import gift.auth.DTO.MemberDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 로그인 DAO 클래스
 */
@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * LoginRepository 생성자
     *
     * @param jdbcTemplate JdbcTemplate 객체
     */
    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 사용자 존재 여부 확인 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 존재 여부
     */
    public boolean isExist(MemberDTO memberDTO) {
        String sql = "SELECT EXISTS(SELECT 1 FROM Users WHERE email = ? and password = ? and isDelete = FALSE)";
        return jdbcTemplate.queryForObject(sql, new Object[]{memberDTO.getEmail(), memberDTO.getPassword()},
            Boolean.class);
    }

    /**
     * 사용자 회원가입 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 존재 여부
     */
    public boolean SignUp(MemberDTO memberDTO) {
        String sql = "INSERT INTO Users (email, password) VALUES (?, ?)";
        if (jdbcTemplate.update(sql, memberDTO.getEmail(), memberDTO.getPassword()) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 사용자 ID 조회 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 ID
     */
    public long getUserId(MemberDTO memberDTO) {
        String sql = "SELECT id FROM Users WHERE email = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{memberDTO.getEmail(), memberDTO.getPassword()},
            Integer.class);
    }
}