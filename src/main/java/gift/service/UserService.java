package gift.service;

import gift.DTO.Token;
import gift.DTO.UserDTO;
import gift.domain.User;
import gift.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    /*
     * 모든 User의 정보를 반환하는 로직
     */
    public List<UserDTO> findAll(){
        List<UserDTO> list = new ArrayList<>();
        List<User> all = userRepository.findAll();
        for (User user : all) {
            list.add(new UserDTO(
                    user.getUserId(),
                    user.getEmail(),
                    user.getPassword()
            ));
        }
        return list;
    }
    /*
     * User의 정보를 저장하는 로직
     */
    public void createUser(UserDTO user){
        userRepository.save(new User(
                user.getUserId(),
                user.getEmail(),
                user.getPassword()
        ));
    }
    /*
     * User의 정보를 email을 기준으로 찾는 로직
     */
    public UserDTO loadOneUser(String userId){
        User user = userRepository.findByUserId(userId);
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getPassword()
        );
    }
    /*
     * userId의 중복 여부를 확인하는 로직
     */
    public boolean isDuplicate(UserDTO user){
        List<UserDTO> all = findAll();
        for (UserDTO userDTO : all) {
            if(userDTO.getUserId().equals(user.getUserId()))
                return true;
        }
        return false;
    }
    /*
     * 로그인을 위한 확인을 해주는 로직
     */
    public boolean login(UserDTO user){
        List<UserDTO> all = findAll();
        for (UserDTO userDTO : all) {
            if(userDTO.getUserId().equals(user.getUserId()) && userDTO.getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }
}
