package gift.permission.repository;

import gift.user.dto.UserResponseDto;
import gift.user.entity.User;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// users db와 통신하는 dao
@Repository
public class PermissionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PermissionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create 처리 메서드
    public void insertUser(User user) {
        // 이미 존재하는 이메일인지 검증
        String email = user.getEmail();
        verifyUserAlreadyExist(email);

        var sql = """
            insert into users (user_id, email, password) values (?, ?, ?)
            """;

        jdbcTemplate.update(sql, user.getUserId(), user.getEmail(),
            user.getPassword());
    }

    // Read 처리 메서드. 관리자 화면에서 사용할 수 있으나, 시간 상의 이유로 메서드만 만듦
    public List<UserResponseDto> selectUsers() {
        var sql = """
            select user_id, email, password
            from users;
            """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new UserResponseDto(
            resultSet.getLong("user_id"),
            resultSet.getString("email"),
            resultSet.getString("password")
        ));
    }

    // 비밀번호 Update 처리 메서드. 관리자 화면 혹은 비밀번호 변경에서 사용할 수 있으나, 시간 상의 이유로 메서드만 만듦
    public void updateUserPassword(User user) {
        var sql = """
            update users set password = ?, where email = ?;
            """;

        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(),
            user.getEmail());
    }

    // Delete 처리 메서드. 관리자 화면 혹은 회원 탈퇴에서 사용할 수 있으나, 시간 상의 이유로 메서드만 만듦
    public void deleteUser(long userId) {
        // 우선 해당하는 id가 있는지부터 검사
        verifyUserExist(userId);

        var sql = """
            delete from users where user_id = ?;
            """;

        jdbcTemplate.update(sql, userId);
    }

    // db에서 특정 email를 갖는 로우를 반환
    public UserResponseDto selectUser(String email) {
        // 존재하는 email를 불러올 수 있도록 검증
        verifyUserExist(email);

        var sql = """
            select user_id, email, password
            from users
            where email = ?;
            """;

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new UserResponseDto(
            resultSet.getLong("user_id"),
            resultSet.getString("email"),
            resultSet.getString("password")
        ), email);
    }

    // db에서 특정 key를 갖는 로우가 있는지 확인하는 메서드
    private boolean exists(long userId) {
        var sql = """
            select user_id
            from users
            where user_id = ?;
            """;

        // 결과의 로우가 존재하는지 반환
        boolean isEmpty = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> resultSet.getString("user_id"), userId)
            .isEmpty();

        return !isEmpty;
    }

    // db에서 특정 email(unique)을 갖는 로우가 있는지 확인하는 메서드. id를 알 수 없는 로그인 시 사용
    private boolean exists(String email) {
        var sql = """
            select email
            from users
            where email = ?;
            """;

        // 결과의 로우가 존재하는지 반환
        boolean isEmpty = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> resultSet.getString("email"), email)
            .isEmpty();

        return !isEmpty;
    }

    // 논리적 검증: 삽입 시에는 key가 중복되지 않아야 한다.
    private void verifyUserAlreadyExist(String email) {
        if (exists(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    // 논리적 검증: 조회 시에는 key가 존재해야 한다.
    private void verifyUserExist(long userId) {
        if (!exists(userId)) {
            throw new NoSuchElementException("존재하지 않는 유저입니다.");
        }
    }

    // 논리적 검증: 조회 시에는 특정 값을 갖는 unique column이 존재해야 한다.
    private void verifyUserExist(String email) {
        if (!exists(email)) {
            throw new NoSuchElementException("회원가입된 email이 아닙니다.");
        }
    }
}
