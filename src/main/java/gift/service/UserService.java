package gift.service;

import gift.DTO.UserDTO;
import gift.domain.User;
import gift.domain.User.CreateUser;
import gift.domain.User.UpdateUser;
import gift.domain.User.UserSimple;
import gift.errorException.BaseHandler;
import gift.mapper.UserMapper;
import gift.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public List<UserSimple> getUserList() {
        return userMapper.UserSimpleList(userRepository.getUserList());
    }

    public UserSimple getUser(long id) {
        if (!userRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");
        }
        return userMapper.UserSimple(userRepository.getUser(id));
    }

    public int createUser(CreateUser create) {
        if (userRepository.existUser(create.getEmail())) {
            throw new BaseHandler(HttpStatus.FORBIDDEN, "중복된 유저가 존재합니다.");
        }
        return userRepository.createUser(create);
    }

    public int updatePassword(long id, UpdateUser update) {
        if (!userRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");
        }
        System.out.println(update.getPassword());
        return userRepository.updatePassword(id,update);
    }

    public int deleteUser(long id) {
        if (!userRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");
        }
        return userRepository.deleteUser(id);
    }
}
