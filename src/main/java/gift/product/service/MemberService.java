package gift.product.service;

import gift.product.dao.MemberDao;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.util.CertifyUtil;
import gift.product.validation.MemberValidation;
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
    private final MemberValidation memberValidation;

    @Autowired
    public MemberService(MemberDao memberDao, CertifyUtil certifyUtil, MemberValidation memberValidation) {
        this.memberDao = memberDao;
        this.certifyUtil = certifyUtil;
        this.memberValidation = memberValidation;
    }

    public ResponseEntity<Map<String, String>> signUp(Member member) {
        memberValidation.signUpValidation(member);
        memberDao.save(new Member(member.getEmail(), certifyUtil.encodingPassword(member.getPassword())));
        return new ResponseEntity<>(responseToken(certifyUtil.generateToken(member.getEmail())), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> login(Member member) {
        Member findMember = memberValidation.loginValidation(member.getEmail());
        if(!findMember.getPassword().equals(certifyUtil.encodingPassword(member.getPassword())))
            throw new LoginFailedException("비밀번호가 틀립니다.");
        memberValidation.login(member.getEmail());
        return new ResponseEntity<>(responseToken(memberValidation.getToken(member.getEmail())), HttpStatus.OK);
    }

    public Map<String, String> responseToken(String token) {
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
