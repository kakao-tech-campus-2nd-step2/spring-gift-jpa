package gift.service;


import gift.entity.Member;
import gift.exception.DataNotFoundException;
import gift.exception.DuplicateUserEmailException;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }


    public void generateUser(Member member) {
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new DuplicateUserEmailException(
                "UserEmail " + member.getEmail() + "already exists."
            );
        }
        memberRepository.save(member);

    }

    public String authenticateUser(Member member) {
        Member loginMember = memberRepository.findByEmailAndPassword(member.getEmail(),
            member.getPassword());

        if (loginMember == null) {
            throw new DataNotFoundException("존재하지 않는 회원이거나 비밀번호가 틀렸습니다.");
        }
        return jwtUtil.generateToken(loginMember.getEmail());

    }

    public Member findById(Long id){
        Optional<Member> member = memberRepository.findById(id);

        if(member.isEmpty()){
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        }
        return member.get();
    }


}
