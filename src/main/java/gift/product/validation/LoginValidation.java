package gift.product.validation;

import gift.product.dao.MemberDao;
import gift.product.model.Member;
import gift.product.util.CertifyUtil;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginValidation {
    private final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    private final MemberDao memberDao;
    private final CertifyUtil certifyUtil;

    @Autowired
    public LoginValidation(MemberDao memberDao, CertifyUtil certifyUtil) {
        this.memberDao = memberDao;
        this.certifyUtil = certifyUtil;
    }

    public boolean login(String email, String password) {
//        if(memberDao.validateMember(email, password)) {
//            tokenMap.put(email, certifyUtil.generateToken(email));
//            return true;
//        }
        return false;
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
