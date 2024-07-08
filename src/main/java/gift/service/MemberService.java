package gift.service;

import gift.dao.MemberDAO;
import gift.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberService {

    private final MemberDAO memberDAO;
    private final JwtService jwtService;

    @Autowired
    public MemberService(MemberDAO memberDAO, JwtService jwtService) {
        this.memberDAO = memberDAO;
        this.jwtService = jwtService;
    }


    public Member register(Member member) throws Exception {
        Member getMember = memberDAO.findByEmail(member.getEmail());

        if (getMember != null) {
            throw new Exception("해당 email의 계정이 이미 존재합니다.");
        }

        return memberDAO.save(member);

    }

    public String login(Member member) throws Exception {
        Member getMember = memberDAO.findByEmail(member.getEmail());

        if (getMember == null || !Objects.equals(getMember.getPassword(), member.getPassword())) {
            throw new Exception("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return jwtService.generateToken(member);
    }

    public Member findById(Long id) {
        return memberDAO.findById(id);
    }

    public Member findByEmail(String email) {
        return memberDAO.findByEmail(email);
    }

}
