package gift.service;

import gift.exception.RepositoryException;
import gift.model.Member;
import gift.model.MemberDTO;
import gift.repository.MemberRepository;
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
        memberRepository.save(member);
    }

    public Member findMemberByCredentials(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new RepositoryException("해당 사용자를 데이터 베이스에서 찾을 수 없습니다."));
    }
}
