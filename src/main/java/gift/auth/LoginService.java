package gift.auth;

import gift.exception.AuthException;
import gift.model.member.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * LoginService 클래스는 로그인 처리와 관련된 비즈니스 로직을 담당합니다.
 */
@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;
    private final JwtToken jwtToken = new JwtToken();

    /**
     * 사용자의 로그인 정보를 확인하고 JWT 토큰을 생성함
     *
     * @param loginDTO 사용자의 로그인 정보가 포함된 Login 객체
     * @return 생성된 JWT 토큰을 포함하는 Token 객체
     * @throws AuthException 사용자가 존재하지 않는 경우 예외를 발생시킴
     */
    public TokenDTO Login(LoginDTO loginDTO) {
        if (!loginRepository.isExist(loginDTO)) {
            throw new AuthException("유저가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
        loginDTO.setId(loginRepository.getUserId(loginDTO));
        return jwtToken.createToken(loginDTO);
    }

    /**
     * 사용자의 회원가입 정보를 확인하고 JWT 토큰을 생성함
     *
     * @param loginDTO 사용자의 회원가입 정보가 포함된 Login 객체
     * @return 생성된 JWT 토큰을 포함하는 Token 객체
     * @throws AuthException 사용자가 회원가입에 실패한 경우 예외를 발생시킴
     */
    public TokenDTO SignUp(LoginDTO loginDTO) {
        if (!loginRepository.SignUp(loginDTO)) {
            throw new AuthException("회원가입에 실패하였습니다.", HttpStatus.FORBIDDEN);
        }
        loginDTO.setId(loginRepository.getUserId(loginDTO));
        return jwtToken.createToken(loginDTO);
    }
}
