package gift.member;

import org.springframework.data.repository.CrudRepository;

import java.sql.*;

// JDBC 에 직접 연결
public interface MemberRepository extends CrudRepository<Member, Long> {
    // h2-console JDBC 연결 할때
    String URL = "jdbc:h2:mem:testdb";
    String USERNAME = "sa";
    String PASSWORD = "";

    // SQL 쿼리
    String SQL_INSERT_MEMBER = "INSERT INTO member (email, password) VALUES (?, ?)";
    String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM member WHERE email = ? AND password = ?";

    // 새로운 회원 저장
    default Member save(Member member) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_MEMBER)) {

            stmt.setString(1, member.email());
            stmt.setString(2, member.password());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("새로운 회원 저장 성공");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return member;
    }

    // 이메일과 비밀번호로 회원 찾기
    default Member findByEmailAndPassword(String email, String password) {
        Member member = null;
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_EMAIL_AND_PASSWORD)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.email(rs.getString("email"));
                member.password(rs.getString("password"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return member;
    }
}