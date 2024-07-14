package gift.service;

import gift.domain.Member;
import gift.dto.request.AuthRequest;
import gift.dto.response.AuthResponse;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public AuthService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse addMember(AuthRequest authRequest) {
        Member requestMember = new Member(authRequest.getEmail(), authRequest.getPassword());
        Member savedMember = memberRepository.save(requestMember);
        return new AuthResponse(jwtUtil.createJWT(savedMember.getId()));
    }

    public AuthResponse login(AuthRequest authRequest) {
        Optional<Member> storedMember = memberRepository.findMemberByEmail(authRequest.getEmail());
        return storedMember.map(member -> new AuthResponse(jwtUtil.createJWT(member.getId()))).orElse(null);
    }

}
