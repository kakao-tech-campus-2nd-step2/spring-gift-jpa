package gift.service;

import gift.domain.User;
import gift.dto.requestDTO.UserLoginRequestDTO;
import gift.dto.requestDTO.UserSignupRequestDTO;
import gift.repository.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserSignupRequestDTO userSignupRequestDTO){
        User user = UserSignupRequestDTO.toEntity(userSignupRequestDTO);
        userRepository.insertUser(user);
    }

    public User findById(Long id){
        return userRepository.selectUserById(id)
            .orElseThrow(()-> new NoSuchElementException("회원의 정보가 일치하지 않습니다."));
    }

    public User findByEmail(UserLoginRequestDTO userLoginRequestDTO){
        return userRepository.selectUserByEmail(userLoginRequestDTO.email())
            .orElseThrow(()-> new NoSuchElementException("회원의 정보가 일치하지 않습니다."));
    }
}
