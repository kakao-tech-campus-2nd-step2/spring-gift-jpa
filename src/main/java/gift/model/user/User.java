package gift.model.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private String id;
    private String email;
    private String password;
    private String name;
    private Role role;

    public User(String id, String email, String password, String name, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    // 비밀번호 검증 메서드
    public boolean verifyPassword(String originalPassword) {
        String hashedPassword = PasswordUtil.hashPasswordWithSalt(originalPassword);
        return hashedPassword.equals(this.password);
    }

    public static User create(String id, String email, String password, String name, Role role) {
        return new User(id, email, PasswordUtil.hashPasswordWithSalt(password), name, role);
    }

}

class PasswordUtil {

    // 비밀번호와 salt를 함께 해싱하는 메서드
    public static String hashPasswordWithSalt(String password) {
        String salt = "secret";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hash = md.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 바이트 배열을 16진수 문자열로 변환하는 메서드
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
