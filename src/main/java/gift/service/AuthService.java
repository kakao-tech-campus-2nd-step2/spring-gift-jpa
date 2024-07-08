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
        if (registeredMember.isEmpty()) {
            return null;
        }
        System.out.println(jwtUtil.getEmailFromJWT(jwtUtil.createJWT(authRequest.getEmail())));
        return new AuthResponse(jwtUtil.createJWT(authRequest.getEmail()));
    }

    public AuthResponse login(AuthRequest authRequest) {
        Optional<Member> storedMember = authRepository.selectMember(authRequest.getEmail());
        if (storedMember.isEmpty()) {
            return null;
        }
        return new AuthResponse(jwtUtil.createJWT(authRequest.getEmail()));
    }

}
