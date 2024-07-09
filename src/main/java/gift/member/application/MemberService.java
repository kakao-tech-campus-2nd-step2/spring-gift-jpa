package gift.member.application;

import gift.error.MemberAlreadyExistsException;
import gift.member.dao.MemberRepository;
import gift.member.entity.Member;
import gift.member.dto.MemberDto;
import gift.error.AuthenticationFailedException;
import gift.auth.security.JwtUtil;
import gift.member.util.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(
            MemberRepository memberRepository,
            JwtUtil jwtUtil
    ) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void registerMember(MemberDto memberDto) {
        // 사용자 계정 중복 검증
        memberRepository.findByEmail(memberDto.email())
                        .ifPresent(member -> {
                            throw new MemberAlreadyExistsException();
                        });

        memberRepository.save(MemberMapper.toEntity(memberDto));
    }

    public String authenticate(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.email())
                .orElseThrow(() -> new AuthenticationFailedException("해당 계정은 존재하지 않습니다."));

        if (!member.getPassword()
                .equals(memberDto.password())) {
            throw new AuthenticationFailedException("비밀번호가 틀렸습니다.");
        }
        
        return jwtUtil.generateToken(member.getId());
    }

}
