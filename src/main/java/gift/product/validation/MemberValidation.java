package gift.product.validation;

import gift.product.repository.MemberRepository;
import gift.product.exception.DuplicateException;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.util.JwtUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemberValidation {
    private final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberValidation(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void signUpValidation(Member member) {
        if(memberRepository.findByEmail(member.getEmail()) != null)
            throw new DuplicateException("이미 가입된 이메일입니다.");
    }

    public Member loginValidation(String email) {
        Member member = memberRepository.findByEmail(email);
        if(member == null)
            throw new LoginFailedException("이메일을 잘못 입력하였습니다.");
        return member;
    }

    public boolean isNull(String str) {
        return str == null;
    }

    public void login(String email) {
        tokenMap.put(email, jwtUtil.generateToken(email));
    }

    public void logout(String email) {
        tokenMap.remove(email);
    }

    public boolean stillLogin(String email) {
        return tokenMap.get(email) != null;
    }

    public String getToken(String email) {
        return tokenMap.get(email);
    }
}
