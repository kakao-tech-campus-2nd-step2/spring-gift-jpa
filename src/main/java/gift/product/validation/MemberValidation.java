package gift.product.validation;

import gift.product.dao.MemberDao;
import gift.product.exception.DuplicateException;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.util.CertifyUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemberValidation {
    private final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    private final MemberDao memberDao;
    private final CertifyUtil certifyUtil;

    @Autowired
    public MemberValidation(MemberDao memberDao, CertifyUtil certifyUtil) {
        this.memberDao = memberDao;
        this.certifyUtil = certifyUtil;
    }

    public void signUpValidation(Member member) {
        if(memberDao.findByEmail(member.getEmail()).isPresent())
            throw new DuplicateException("이미 가입된 이메일입니다.");
    }

    public Member loginValidation(String email) {
        Optional<Member> member = memberDao.findByEmail(email);
        if(member.isEmpty())
            throw new LoginFailedException("잘못된 이메일 입니다.");
        return member.get();
    }

    public boolean isNull(String str) {
        return str == null;
    }

    public void login(String email) {
        tokenMap.put(email, certifyUtil.generateToken(email));
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
