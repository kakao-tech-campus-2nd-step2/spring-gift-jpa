package gift.service;

import gift.dto.LoginResultDto;
import gift.dto.MemberDto;
import gift.jwt.JwtUtil;
import gift.model.member.Member;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    public boolean registerNewMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password());
        if(memberRepository.findByEmail(member.getEmail()).isEmpty()){
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    public String returnToken(MemberDto memberDto){
        Member member = new Member(memberDto.email(),memberDto.password());
        return jwtUtil.generateToken(member);
    }

    public LoginResultDto loginMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password());
        Optional<Member> registeredMember = memberRepository.findByEmail(member.getEmail());
        if (registeredMember.isPresent() && member.isPasswordEqual(registeredMember.get().getPassword())) {
            String token = jwtUtil.generateToken(member);
            return new LoginResultDto(token, true);
        }
        return new LoginResultDto(null, false);
    }

    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }
}
