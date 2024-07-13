package gift.service;

import gift.authorization.JwtUtil;
import gift.dto.MemberDTO;
import gift.dto.TokenLoginRequestDTO;
import gift.entity.Member;
import gift.exception.DuplicateValueException;
import gift.repository.MemberRepository;
import io.jsonwebtoken.JwtException;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository repository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository repository , JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public String signUp(MemberDTO memberDTO) {
        String email = memberDTO.email();
        repository.findByEmail(email)
                .ifPresent(value -> {
                    throw new DuplicateValueException("중복된 id입니다");
                });
        Member member = toEntity(memberDTO);
        repository.save(member);
        String token = jwtUtil.generateToken(member);
        return token;
    }

    public String login(MemberDTO memberDTO) {
        String email = memberDTO.email();
        String password = memberDTO.password();
        Member existingMember = repository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new DuplicateValueException("로그인이 실패하였습니다."));
        String token = jwtUtil.generateToken(existingMember);
        return token;
    }

    public void tokenLogin(TokenLoginRequestDTO tokenLoginRequestDTO) {
        if(jwtUtil.isNotValidToken(tokenLoginRequestDTO)){
            throw new JwtException("service - 토큰 인증이 불가능합니다");
        }
    }

    @Description("임시 확인용 service")
    public List<Member> getAllUsers() {
        return repository.findAll();
    }

    public Member toEntity(MemberDTO dto) {
        Member member = new Member();
        member.setEmail(dto.email());
        member.setPassword(dto.password());
        return member;
    }

}