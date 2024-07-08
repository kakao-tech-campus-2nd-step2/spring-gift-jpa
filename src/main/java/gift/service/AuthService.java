package gift.service;

import gift.domain.Member;
import gift.dto.AuthRequest;
import gift.dto.AuthResponse;
import gift.repository.AuthRepository;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil) {
        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse addMember(AuthRequest authRequest) {
        authRepository.insertMember(authRequest);
        Optional<Member> registeredMember = authRepository.selectMember(authRequest.getEmail());
        return registeredMember.map(member -> new AuthResponse(jwtUtil.createJWT(member.getId()))).orElse(null);
    }

    public AuthResponse login(AuthRequest authRequest) {
        Optional<Member> storedMember = authRepository.selectMember(authRequest.getEmail());
        return storedMember.map(member -> new AuthResponse(jwtUtil.createJWT(member.getId()))).orElse(null);
    }

}
