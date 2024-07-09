package gift.service;

import gift.dao.MemberDao;
import gift.vo.Member;
import gift.vo.MemberRole;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final JwtUtil jwtUtil;

    public MemberService(MemberDao memberDao, JwtUtil jwtUtil) {
        this.memberDao = memberDao;
        this.jwtUtil = jwtUtil;
    }

    public String login(Member member) {
        String email = member.getEmail();
        Member foundMember = memberDao.findMemberByEmailAndPassword(email, member.getPassword());
        foundMember.validateEmail(email); // 이메일 검증
        return createJwtToken(email, member.getRole());
    }

    public String join(Member member) {
        Boolean isSuccess = memberDao.addMember(member);
        if (!isSuccess) {
            throw new RuntimeException("회원가입에 실패했습니다. 이미 가입된 이메일 주소일 수 있습니다. ");
        }
        return login(member);
    }

    public String createJwtToken(String email, MemberRole role) {
        return jwtUtil.generateToken(email, role);
    }

}
