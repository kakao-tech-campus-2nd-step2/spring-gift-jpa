package gift.product.service;

import gift.product.repository.MemberRepository;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.util.JwtUtil;
import gift.product.validation.MemberValidation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberValidation memberValidation;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil, MemberValidation memberValidation, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.memberValidation = memberValidation;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Map<String, String>> signUp(Member member) {
        memberValidation.signUpValidation(member);
        memberRepository.save(new Member(member.getEmail(), passwordEncoder.encode(member.getPassword())));
        return new ResponseEntity<>(responseToken(jwtUtil.generateToken(member.getEmail())), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> login(Member member) {
        Member findMember = memberValidation.loginValidation(member.getEmail());

        if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword()))
            throw new LoginFailedException("비밀번호가 틀립니다.");

        return new ResponseEntity<>(responseToken(jwtUtil.generateToken(member.getEmail())), HttpStatus.OK);
    }

    public Map<String, String> responseToken(String token) {
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
