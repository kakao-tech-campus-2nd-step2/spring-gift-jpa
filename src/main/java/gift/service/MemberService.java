package gift.service;

import gift.authorization.JwtUtil;
import gift.dto.TokenLoginRequestDTO;
import gift.dto.MemberDTO;
import gift.entity.Member;
import gift.exceptionhandler.DuplicateValueException;
import gift.repository.MemberRepository;
import io.jsonwebtoken.JwtException;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository repository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository repository , JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public Member toEntity(MemberDTO dto) {
        Member member = new Member();
        member.setEmail(dto.email());
        member.setPassword(dto.password());
        return member;
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
        Optional<Member> existingMember = repository.findByEmail(email);
        existingMember.orElseThrow(() -> new DuplicateValueException("id와 비밀번호를 다시 확인하세요"));
        if(memberDTO
                .password()
                .equals(existingMember.get()
                .getPassword())) {
            String token = jwtUtil.generateToken(existingMember.get());
            return token;
        }
        throw new DuplicateValueException("id와 비밀번호를 다시 확인하세요");
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

}