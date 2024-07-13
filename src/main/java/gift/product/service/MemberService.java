package gift.product.service;

import gift.product.dto.MemberDTO;
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

    public ResponseEntity<Map<String, String>> signUp(MemberDTO memberDTO) {
        System.out.println("[MemberService] signUp()");
        memberValidation.signUpValidation(memberDTO.getEmail());

        Member member = convertDTOToMember(memberDTO);
        memberRepository.save(member);

        String token = jwtUtil.generateToken(member.getEmail());
        return new ResponseEntity<>(responseToken(token), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> login(MemberDTO memberDTO) {
        memberValidation.loginValidation(memberDTO);

        Member member = convertDTOToMember(memberDTO);
        String token = jwtUtil.generateToken(member.getEmail());
        return new ResponseEntity<>(responseToken(token), HttpStatus.OK);
    }

    public Map<String, String> responseToken(String token) {
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    public Member convertDTOToMember(MemberDTO memberDTO) {
        return new Member(
                memberDTO.getEmail(),
                passwordEncoder.encode(memberDTO.getPassword())
        );
    }
}
