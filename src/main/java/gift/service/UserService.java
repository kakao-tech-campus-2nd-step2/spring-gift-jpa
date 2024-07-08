package gift.service;


import gift.entity.Member;
import gift.exception.DuplicateUserEmailException;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public UserService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }



    public void generateUser(Member member) {
        if(memberRepository.findByEmail(member.getEmail())!=null){
            throw new DuplicateUserEmailException(
                "UserEmail " + member.getEmail()+"already exists."
            );
        }
        memberRepository.save(member);

    }

    public String authenticateUser(Member member) {
        Member loginMember = memberRepository.findByEmailAndPassword(member.getEmail(),
            member.getPassword());
        return jwtUtil.generateToken(loginMember.getEmail());

    }


}
