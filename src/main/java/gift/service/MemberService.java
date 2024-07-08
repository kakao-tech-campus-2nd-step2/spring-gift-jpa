package gift.service;

import gift.exception.RepositoryException;
import gift.model.Member;
import gift.model.MemberDTO;
import gift.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void createMember(MemberDTO memberDTO) {
        Member member = new Member(memberDTO.id(), memberDTO.email(), memberDTO.password());
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new RepositoryException("해당 사용자를 데이터 베이스에 저장할 수 없습니다.");
        }
    }

    public String getMemberByEmailAndPassword(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new RepositoryException("해당 사용자를 데이터 베이스에서 찾을 수 없습니다."));
        return jwtUtil.generateToken(member.getId());
    }
}
