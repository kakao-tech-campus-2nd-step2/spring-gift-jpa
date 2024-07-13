package gift.dto;

public class UserDTO {
    public static class LoginDTO {
        String email;
        String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class SignUpDTO {
        String email;
        String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class Token {
        String token;

        public Token(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
