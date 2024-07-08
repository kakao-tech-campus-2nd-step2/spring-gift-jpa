package gift.service;

import gift.dao.MemberDao;
import gift.dto.LoginResultDto;
import gift.dto.MemberDto;
import gift.jwt.JwtUtil;
import gift.model.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberDao memberDao, JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.memberDao = memberDao;
    }

    public boolean registerNewMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password());
        if(memberDao.findByEmail(member.getEmail()) == null){
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
        Member registeredMember = memberDao.findByEmail(member.getEmail());
        if (registeredMember != null &&member.getPassword().equals(registeredMember.getPassword())) {
            String token = jwtUtil.generateToken(member);
            return new LoginResultDto(token, true);
        }
        return new LoginResultDto(null, false);
    }

    public Member findByEmail(String email){
        return memberDao.findByEmail(email);
    }
}
