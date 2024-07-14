package gift.service;


import gift.Util.JWTUtil;

import gift.dto.user.LoginDTO;
import gift.dto.user.SignUpDTO;
import gift.dto.user.Token;

import gift.entity.User;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtil jwtUtil;

    public void signUp(SignUpDTO dto) {
        if(userRepository.findByEmail(dto.email()).isPresent())
            throw new BadRequestException("이미 존재하는 계정");
        userRepository.save(new User(dto.email(), dto.password()));
    }

    public Token signIn(LoginDTO loginDTO){
        Optional<User> user = userRepository.findByEmail(loginDTO.email());
        if(user.isEmpty())
            throw new NotFoundException("존재하지 않는 계정");
        User user1 = user.get();
        if (!user1.getPassword().equals( loginDTO.password()))
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");

        return new Token(jwtUtil.generateToken(user1));


    }
}
