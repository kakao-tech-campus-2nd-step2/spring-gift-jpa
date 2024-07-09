package gift.Service;

import gift.DAO.UserDAO;
import gift.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final TokenService tokenService;

    @Autowired
    public UserService(UserDAO userDAO, TokenService tokenService) {
        this.userDAO = userDAO;
        this.tokenService = tokenService;
    }

    public UserEntity getUserByToken(String token) {
        String email = tokenService.getEmailFromToken(token);
        if (email == null || !tokenService.validateToken(token, email)) {
            return null;
        }
        return (UserEntity) userDAO.findByEmail(email);
    }
}