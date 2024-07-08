package gift.service;

import gift.dao.UserDAO;
import gift.entity.User;
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

    public User getUserByToken(String token) {
        String email = tokenService.extractEmail(token);
        return userDAO.findByEmail(email);
    }
}
