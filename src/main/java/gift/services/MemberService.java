package gift.services;


import gift.JWTUtil;
import gift.Member;
import gift.MemberDto;
import gift.classes.Exceptions.EmailAlreadyExistsException;
import gift.repositories.MemberRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private long currentMemberId =1;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository, JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void register(MemberDto memberDto) throws EmailAlreadyExistsException {

        Member member = new Member(
            null,
            memberDto.getEmail(),
            memberDto.getPassword(),
            memberDto.getRole()
        );
        Member existingMember = memberRepository.find(member);

        if (existingMember != null) {
            throw new EmailAlreadyExistsException();
        }

        if(member.getMemberId() == null) {
            member.setMemberId(currentMemberId++);
        }

        memberRepository.register(member);
    }

    public String login(MemberDto memberDto) {

        Member member = new Member(memberDto.getMemberId(), memberDto.getEmail(), memberDto.getPassword(), memberDto.getRole());
        Member existingMember = memberRepository.find(member);

        if (existingMember == null) {
            throw new NoSuchElementException("Email is not exist. ");
        }

        String token = jwtUtil.createJwt(member.getEmail());
        return token;

    }

    public MemberDto getLoginUser(String token){
        String email = jwtUtil.getLoginEmail(token);
        Member existingMember = memberRepository.findByEmail(email);
        MemberDto memberDto = new MemberDto(existingMember.getMemberId(),
            existingMember.getEmail(),
            existingMember.getPassword(),
            existingMember.getRole());
        return memberDto;
    }

}
