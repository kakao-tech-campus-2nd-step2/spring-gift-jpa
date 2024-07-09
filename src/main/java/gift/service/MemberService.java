package gift.service;

import gift.domain.Member;
import gift.repository.MemberDao;
import gift.util.JwtUtil;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final JwtUtil jwtUtil;

    public MemberService(MemberDao memberDao, JwtUtil jwtUtil) {
        this.memberDao = memberDao;
        this.jwtUtil = jwtUtil;
    }

    public String generateMember(String email, String password) {
        Member member = new Member(email, password);
        memberDao.insertMember(member);
        return JwtUtil.generateToken(email, password);
    }

    public String authenticateMember(String email, String password) {
        Member member = new Member(email, password);
        Optional<Member> existingMember = Optional.ofNullable(
            memberDao.selectMember(member).orElseThrow(() -> {
                throw new NoSuchElementException("해당하는 사용자 데이터가 없습니다.");
            }));
        return jwtUtil.generateToken(email, password);
    }

    public Long getMemberIdByEmail(String email) {
        return memberDao.selectMemberIdByEmail(email);
    }
}
