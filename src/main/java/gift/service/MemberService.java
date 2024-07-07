package gift.service;

import static gift.util.JwtUtil.generateJwtToken;
import gift.dto.MemberDto;
import gift.exception.ForbiddenException;
import gift.domain.Member;
import gift.repository.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public String registerMember(MemberDto memberDto) {
        if (memberDao.findByEmail(memberDto.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        Member newMember = new Member(memberDto.getEmail(), memberDto.getPassword());
        memberDao.save(newMember);
        return newMember.getEmail();
    }

    public String login(MemberDto memberDto) {
        Member member = memberDao.findByEmail(memberDto.getEmail());
        if (member == null || !memberDto.getPassword().equals(member.getPassword())) {
            throw new ForbiddenException("사용자 없거나 비밀번호 틀림");
        }

        return generateJwtToken(member);
    }

    public Member findById(long id) {
        Member member = memberDao.findById(id);
        return member;
    }
}
