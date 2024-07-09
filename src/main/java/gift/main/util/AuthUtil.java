package gift.main.util;

import gift.main.dto.UserVo;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    public static UserVo getSessionUser(HttpSession httpSession) {
        return (UserVo) httpSession.getAttribute("user");
    }

}


