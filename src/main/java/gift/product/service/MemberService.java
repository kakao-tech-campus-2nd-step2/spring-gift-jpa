package gift.product.service;

import gift.product.dao.MemberDao;
import gift.product.model.Member;
import gift.product.util.CertifyUtil;
import gift.product.validation.LoginValidation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final CertifyUtil certifyUtil;
    private final LoginValidation loginValidation;

    @Autowired
    public MemberService(MemberDao memberDao, CertifyUtil certifyUtil, LoginValidation loginValidation) {
        this.memberDao = memberDao;
        this.certifyUtil = certifyUtil;
        this.loginValidation = loginValidation;
    }

    public ResponseEntity<Map<String, String>> signUp(Map<String, String> request) {
        String email = request.get("email");

        if(memberDao.findByEmail(email).isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        return new ResponseEntity<>(responseToken(certifyUtil.generateToken(email)), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> login(Map<String, String> request) {
        String email = request.get("email");

        if(loginValidation.login(email, certifyUtil.encodingPassword(request.get("password"))))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(responseToken(loginValidation.getToken(email)), HttpStatus.OK);
    }

//    public boolean isExistsMember(String email) {
//        return memberDao.isExistsMember(email);
//    }

    public Map<String, String> responseToken(String token) {
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
