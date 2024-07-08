package gift.service;

import gift.controller.dto.ChangePasswordDTO;
import gift.controller.dto.TokenResponseDTO;
import gift.controller.dto.UserDTO;
import gift.domain.UserInfo;
import gift.repository.UserInfoRepository;
import gift.utils.JwtTokenProvider;
import gift.utils.error.UserAlreadyExistsException;
import gift.utils.error.UserNotFoundException;
import gift.utils.error.UserPasswordNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public UserService(UserInfoRepository userInfoRepository, JwtTokenProvider jwtTokenProvider) {
        this.userInfoRepository = userInfoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponseDTO registerUser(UserDTO userDTO) {
        UserInfo userInfo = new UserInfo(userDTO.getPassword(),userDTO.getEmail());
        Boolean result = userInfoRepository.save(userInfo);
        if (!result) {
            throw new UserAlreadyExistsException("User Already Exist");
        }
        return new TokenResponseDTO(jwtTokenProvider.createToken(userDTO.getEmail()));
    }

    public TokenResponseDTO login(UserDTO userDTO){
        UserInfo userInfo = new UserInfo(userDTO.getPassword(),userDTO.getEmail());
        Optional<UserInfo> byEmail = userInfoRepository.findByEmail(userInfo.getEmail());
        if (byEmail.isEmpty()){
            throw new UserNotFoundException("User NOT FOUND");
        }
        return new TokenResponseDTO(jwtTokenProvider.createToken(userDTO.getEmail()));
    }

    public boolean changePassword(ChangePasswordDTO changePasswordDTO){
        UserInfo userInfo = userInfoRepository.findByEmail(changePasswordDTO.getEmail())
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!userInfo.getPassword().equals(changePasswordDTO.getCurrentPassword())){
            throw new UserPasswordNotFoundException("Password not found");
        }
        userInfo.setPassword(changePasswordDTO.getNewPassword());
        Boolean result = userInfoRepository.changePassword(userInfo);
        return true;
    }

    public UserDTO findPassword(String email){
        Optional<UserInfo> byEmail = userInfoRepository.findByEmail(email);
        if (byEmail.isEmpty()){
            throw new UserNotFoundException("User NOT FOUND");
        }
        return new UserDTO(byEmail.get().getPassword(),email);
    }


}
