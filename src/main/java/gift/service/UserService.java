package gift.service;


import gift.Util.JWTUtil;
import gift.dto.UserDTO;

import gift.entity.User;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void signUp(UserDTO.SignUpDTO dto) {
        if(userRepository.isExistAccount(dto.getEmail()))
            throw new BadRequestException("이미 존재하는 계정");
        userRepository.saveUser(new User(dto.getEmail(), dto.getPassword()));
    }

    public UserDTO.Token signIn(UserDTO.LoginDTO loginDTO){

        if(!userRepository.isExistAccount(loginDTO.getEmail()))
            throw new NotFoundException("존재하지 않는 계정");
        User user = userRepository.findUserbyID(loginDTO.getEmail());
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");

        return new UserDTO.Token(JWTUtil.generateToken(user));

    }
}
