package gift.service;

import gift.dao.MemberDao;
import gift.model.Member;
import gift.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberDao.registerMember(member);
        return jwtTokenProvider.createToken(member.getEmail());
    }

    public String login(String email, String password) {
        Member member = memberDao.findByEmail(email);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return jwtTokenProvider.createToken(email);
        }
        throw new RuntimeException("Invalid email or password");
    }

    public Member findByEmail(String email) {
        return memberDao.findByEmail(email);
    }
}
