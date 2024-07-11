package gift.product.validation;

import gift.product.dao.MemberDao;
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

    private final MemberDao memberDao;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberValidation(MemberDao memberDao, JwtUtil jwtUtil) {
        this.memberDao = memberDao;
        this.jwtUtil = jwtUtil;
    }

    public void signUpValidation(Member member) {
        if(memberDao.findByEmail(member.getEmail()) == null)
            throw new DuplicateException("이미 가입된 이메일입니다.");
    }

    public Member loginValidation(String email) {
        Member member = memberDao.findByEmail(email);
        if(member == null)
            throw new LoginFailedException("잘못된 이메일 입니다.");
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
