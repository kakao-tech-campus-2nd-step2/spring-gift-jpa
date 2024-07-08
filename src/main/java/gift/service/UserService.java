package gift.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gift.dao.UserDao;
import gift.domain.User;
import gift.dto.UserDto;
import gift.dto.request.LoginRequest;
import gift.exception.CustomException;
import gift.util.JwtUtil;

@Service
public class UserService {

    private UserDao userDao;
    private JwtUtil jwtUtil;

    public UserService(UserDao userDao, JwtUtil jwtUtil){
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    public UserDto findByEmail(String email){
        User user = userDao.findByEmail(email)
            .orElseThrow(() -> new CustomException("User with email " + email + " not found", HttpStatus.NOT_FOUND));
        return user.toDto(user);
    }

    public void addUser(UserDto userDto){
         if(userDao.findByEmail(userDto.getEmail()).isEmpty()){
            User user = userDto.toEntity(userDto);
            userDao.insertUser(user); 
        }else{
            throw new CustomException("User with email " + userDto.getEmail() + "exists", HttpStatus.CONFLICT);
        }
    }

    public String generateToken(String email){
        UserDto userDto = findByEmail(email);
        return jwtUtil.generateToken(userDto);
    }

    public String authenticateUser(LoginRequest loginRequest){
        User user = userDao.findByRequest(loginRequest.getEmail(), loginRequest.getPassword())
            .orElseThrow(() -> new CustomException("User with Request not found", HttpStatus.NOT_FOUND));
        UserDto userDto = user.toDto(user);
        return generateToken(userDto.getEmail());
    }
    
}
