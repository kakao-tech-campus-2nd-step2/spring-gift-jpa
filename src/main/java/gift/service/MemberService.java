package gift.service;

import gift.domain.member.Member;
import gift.domain.member.MemberRepository;
import gift.dto.MemberRegisterRequestDto;
import gift.dto.MemberRegisterResponseDto;
import gift.dto.MemberRequestDto;
import gift.exception.InvalidPasswordException;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public MemberRegisterResponseDto signUpMember(MemberRegisterRequestDto requestDto) {
        if (memberRepository.isAlreadyExist(requestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Member member = new Member(requestDto.email(), requestDto.email(), requestDto.password(), 1);
        Member registered = memberRepository.registerMember(member);
        return new MemberRegisterResponseDto(registered.getId(), registered.getEmail(), requestDto.name());
    }
    public String loginMember(MemberRequestDto requestDto){
        Member member = memberRepository.findByEmail(requestDto.getEmail());

        if (!member.isMatch(requestDto.getPassword())){
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(member.getEmail());
    }

    public Member findMemberByEmail(String email){
        if (memberRepository.isNotValidMember(email)) {
            throw new IllegalArgumentException("유효하지 않은 회원 정보입니다.");
        }
        return memberRepository.findByEmail(email);
    }
}
