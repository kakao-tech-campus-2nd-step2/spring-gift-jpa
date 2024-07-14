package gift.security.hash;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }
}
