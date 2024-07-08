package gift.service;

import gift.exception.LoginErrorException;
import gift.model.Member;
import gift.repository.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Member join(String email, String password) {
        return memberDao.insert(new Member(email, password));
    }

    public Member login(String email, String password) {
        Member loginedMember = memberDao.getMemberByEmail(email);

        if(!loginedMember.login(email, password)) {
            throw new LoginErrorException();
        }
        return loginedMember;
    }

}
