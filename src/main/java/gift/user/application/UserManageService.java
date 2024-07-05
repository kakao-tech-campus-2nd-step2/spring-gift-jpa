package gift.user.application;

import gift.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManageService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public String registerUser(String name, String email) {
        // register user
        String token = jwtTokenUtil.generateToken(name);

        return token;
    }

    public static class CreateUserRequestDTO {

        private static final int MAX_INPUT_LENGTH = 255;

        private String name;

        private String email;

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
