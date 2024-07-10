package gift.member;

import gift.exception.AlreadyExistMember;
import gift.exception.NotFoundMember;
import gift.login.JwtTokenUtil;
import gift.login.TokenResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import gift.login.TokenResponseDto;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    // 회원 인서트
//    public void postMember(Member member) {
////        memberDao.insertMember(member);
//        memberRepository.saveAndFlush(member);
//    }
//
//    // 회원으로 회원 조회, 존재하는 건지 보는 건가봐
//    public Optional<Member> getMember(Member member) {
////        return memberDao.findMember(member);
////        memberRepository.findOne(member);
//        return  memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword());
//    }
//
//    // 회원이메일로 회원조회 토큰 관련된거겠지?
//    public Member getMemberByEmail(String userEmail) {
////        return memberDao.findMemberById(userEmail);
//        return memberRepository.findByEmail(userEmail);
//    }



    public ResponseEntity<TokenResponseDto> register(Member member) throws AlreadyExistMember {
        Optional<Member> existMember = memberRepository.findByEmailAndPassword(member.getEmail(),member.getPassword());
        if (!existMember.isPresent()) {
            memberRepository.saveAndFlush(member);
            String token = JwtTokenUtil.generateToken(member.getEmail());
            return ResponseEntity.ok((new TokenResponseDto(token)));
        } else {
            throw new AlreadyExistMember("이미 회원정보가 존재합니다");
        }
    }

    public ResponseEntity<TokenResponseDto> login(Member member) throws NotFoundMember {
        Optional<Member> existMember = memberRepository.findByEmailAndPassword(member.getEmail(),member.getPassword());
        if (existMember.isPresent()) {
            String token = JwtTokenUtil.generateToken(member.getEmail());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } else {
            throw new NotFoundMember("회원정보가 존재하지 않습니다");
        }
    }

}
