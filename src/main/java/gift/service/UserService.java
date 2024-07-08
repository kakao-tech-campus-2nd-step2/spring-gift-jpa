package gift.service;

import gift.dto.UserRequestDto;
import gift.exception.UserNotFoundException;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserRequestDto userRequestDto){
        userRepository.save(userRequestDto.toEntity());
    }

    public boolean authenticate(String email, String password) {
        userRepository.findByPasswordAndEmail(email, password)
                .orElseThrow(() -> new UserNotFoundException("해당 정보를 가진 유저가 존재하지 않습니다."));
        return true;
    }
}
