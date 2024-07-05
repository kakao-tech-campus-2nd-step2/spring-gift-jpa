package gift.service;

import gift.domain.Member;
import gift.dto.MemberRequestDto;
import gift.dto.MemberResponseDto;
import gift.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponseDto registerMember(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Member member = new Member();
        member.setEmail(requestDto.getEmail());
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member savedMember = memberRepository.save(member);

        return new MemberResponseDto(savedMember.getId(), savedMember.getEmail());
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 " + email + " 가진 회원이 없습니다."));
    }

}
