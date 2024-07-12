package gift.service;

import gift.domain.User;
import gift.dto.requestDTO.UserLoginRequestDTO;
import gift.dto.requestDTO.UserSignupRequestDTO;
import gift.repository.JpaUserRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public void join(UserSignupRequestDTO userSignupRequestDTO) {
        User user = UserSignupRequestDTO.toEntity(userSignupRequestDTO);
        jpaUserRepository.save(user);
    }

    public User findById(Long id) {
        return jpaUserRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("회원의 정보가 일치하지 않습니다."));
    }

    public User findByEmail(UserLoginRequestDTO userLoginRequestDTO) {
        return jpaUserRepository.findByEmail(userLoginRequestDTO.email())
            .orElseThrow(() -> new NoSuchElementException("회원의 정보가 일치하지 않습니다."));
    }
}
