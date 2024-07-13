package gift.auth.service;

import gift.auth.DTO.MemberDTO;
import gift.auth.DTO.TokenDTO;
import gift.auth.exception.AuthException;
import gift.auth.utill.JwtToken;
import gift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * LoginService 클래스는 로그인 처리와 관련된 비즈니스 로직을 담당합니다.
 */
@Service
public class LoginService {

    @Autowired
    private MemberService memberService;
    private final JwtToken jwtToken = new JwtToken();

    /**
     * 사용자의 로그인 정보를 확인하고 JWT 토큰을 생성함
     *
     * @param memberDTO 사용자의 로그인 정보가 포함된 Login 객체
     * @return 생성된 JWT 토큰을 포함하는 Token 객체
     * @throws AuthException 사용자가 존재하지 않는 경우 예외를 발생시킴
     */
    public TokenDTO Login(MemberDTO memberDTO) {
        if (!memberService.isExist(memberDTO)) {
            throw new AuthException("유저가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
        memberDTO.setId(memberService.getUserId(memberDTO));
        return jwtToken.createToken(memberDTO);
    }

    /**
     * 사용자의 회원가입 정보를 확인하고 JWT 토큰을 생성함
     *
     * @param memberDTO 사용자의 회원가입 정보가 포함된 Login 객체
     * @return 생성된 JWT 토큰을 포함하는 Token 객체
     * @throws AuthException 사용자가 회원가입에 실패한 경우 예외를 발생시킴
     */
    public TokenDTO SignUp(MemberDTO memberDTO) {
        if (!memberService.signUp(memberDTO)) {
            throw new AuthException("회원가입에 실패하였습니다.", HttpStatus.FORBIDDEN);
        }
        memberDTO.setId(memberService.getUserId(memberDTO));
        return jwtToken.createToken(memberDTO);
    }
}
