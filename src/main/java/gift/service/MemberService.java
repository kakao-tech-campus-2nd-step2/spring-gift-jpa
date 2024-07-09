package gift.service;

import gift.dao.MemberDao;
import gift.dto.LoginResultDto;
import gift.dto.MemberDto;
import gift.jwt.JwtUtil;
import gift.model.member.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberDao memberDao;

    private final JwtUtil jwtUtil;

    public MemberService(MemberDao memberDao, JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.memberDao = memberDao;
    }

    public boolean registerNewMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password());
        if(memberDao.findByEmail(member.getEmail()).isEmpty()){
            memberDao.registerNewMember(member);
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
        Optional<Member> registeredMember = memberDao.findByEmail(member.getEmail());
        if (registeredMember.isPresent() && member.isPasswordEqual(registeredMember.get().getPassword())) {
            String token = jwtUtil.generateToken(member);
            return new LoginResultDto(token, true);
        }
        return new LoginResultDto(null, false);
    }

    public Optional<Member> findByEmail(String email){
        return memberDao.findByEmail(email);
    }
}
