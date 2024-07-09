package gift.member;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void postMember(Member member) {
        memberDao.insertMember(member);
    }

    public Optional<Member> getMember(Member member) {
        return memberDao.findMember(member);
    }

    public Member getMemberById(String userEmail) {
        return memberDao.findMemberById(userEmail);
    }
}
