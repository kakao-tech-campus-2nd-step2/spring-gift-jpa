package gift.service;

import gift.DTO.User.UserRequest;
import gift.DTO.User.UserResponse;
import gift.domain.Product;
import gift.domain.User;
import gift.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<UserResponse> findAll(int page, int size){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<User> all = userRepository.findAll(pageable);

        return all.map(this::convertUserToUserRes);
    }
    /*
     * User 엔티티 클래스를 UserResponse DTO 클래스로 변경해주는 메서드
     */
    private UserResponse convertUserToUserRes(User user){
        return new UserResponse(user);
    }
    /*
     * User의 정보를 userId를 기준으로 찾는 로직
     */
    public UserResponse loadOneUser(String userId){
        User user = userRepository.findByUserId(userId);
        return new UserResponse(user);
    }
    /*
     * 위와 동일, 오버로딩
     */
    public UserResponse loadOneUser(Long id){
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return new UserResponse(user);
    }
    /*
     * User의 정보를 저장하는 로직
     */
    @Transactional
    public void createUser(UserRequest user){
        userRepository.save(new User(
                user.getUserId(),
                user.getEmail(),
                user.getPassword()
        ));
    }
    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }
    /*
     * user 정보를 업데이트하는 로직
     */
    @Transactional
    public void update(UserRequest user){
        User byUserId = userRepository.findByUserId(user.getUserId());
        byUserId.updateEntity(user.getEmail(), user.getPassword());
    }
    /*
     * userId의 중복 여부를 확인하는 로직
     */
    public boolean isDuplicate(UserRequest user){
        return userRepository.existsByUserId(user.getUserId());
    }
    /*
     * 위와 동일 ( overloading )
     */
    public boolean isDuplicate(Long id){
        return userRepository.existsById(id);
    }
    /*
     * 로그인을 위한 확인을 해주는 로직
     */
    public boolean login(UserRequest user){
        return userRepository.existsByUserIdAndPassword(user.getUserId(), user.getPassword());
    }
    /*
     * user 정보를 삭제하는 로직
     */
}
