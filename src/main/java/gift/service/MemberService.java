package gift.service;

import gift.dto.MemberDTO;
import gift.util.JwtUtil;
import gift.util.TokenBlacklist;
import gift.model.Member;
import gift.model.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenBlacklist tokenBlacklist;

    public MemberService(MemberRepository memberRepository, TokenBlacklist tokenBlacklist) {
        this.memberRepository = memberRepository;
        this.tokenBlacklist = tokenBlacklist;
    }

    public String register(@Valid MemberDTO memberDTO) {
        memberRepository.findByEmail(memberDTO.getEmail())
                .ifPresent(existingMember -> {
                    throw new DuplicateKeyException("이미 존재하는 이메일입니다.");
                });
        Member member = new Member(null, memberDTO.getEmail(), memberDTO.getPassword(), null);
        Member savedMember = memberRepository.save(member);
        String token = JwtUtil.generateToken(savedMember.getEmail());
        savedMember.setActiveToken(token);
        memberRepository.save(savedMember);
        return token;
    }

    public String login(MemberDTO memberDTO) {
        Member existingMember = memberRepository.findByEmail(memberDTO.getEmail())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 이메일 또는 잘못된 비밀번호입니다."));
        if (!existingMember.getPassword().equals(memberDTO.getPassword())) {
            throw new NoSuchElementException("존재하지 않는 이메일 또는 잘못된 비밀번호입니다.");
        }
        String token = JwtUtil.generateToken(existingMember.getEmail());
        existingMember.setActiveToken(token);
        memberRepository.save(existingMember);
        return token;
    }

    public void logout(String token) {
        Member member = memberRepository.findByActiveToken(token)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 토큰입니다."));
        member.setActiveToken(null);
        memberRepository.save(member);
        tokenBlacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
