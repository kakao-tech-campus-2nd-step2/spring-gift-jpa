package gift.service;

import gift.entity.Member;
import gift.exception.AuthException;
import gift.exception.BusinessException;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public String registerMember(String email, String password) {
//        if (memberRepository.findIdByEmail(email).isPresent()) {
//            throw new AuthException("이미 사용중인 이메일 입니다.");
//        }
        Member member = new Member(email, password);
        memberRepository.save(member);
        return JwtUtil.generateToken(email);
    }

    public String authenticateMember(String email, String password) {
//        if (memberRepository.findByEmailAndPassword(email, password).isEmpty()) {
//            throw new AuthException("로그인 정보와 일치하는 사용자가 없습니다.");
//        }
        return JwtUtil.generateToken(email);
    }

    public Optional<Long> getMemberIdByEmail(String email) {
        if (memberRepository.findIdByEmail(email).isEmpty()) {
            throw new BusinessException("해당 이메일로 등록된 사용자 정보가 없습니다.");
        }
        return memberRepository.findIdByEmail(email);
    }
}