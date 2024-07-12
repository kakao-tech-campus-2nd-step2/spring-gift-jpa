package gift.member.application;

import gift.auth.security.JwtUtil;
import gift.error.CustomException;
import gift.error.ErrorCode;
import gift.member.dao.MemberRepository;
import gift.member.dto.MemberDto;
import gift.member.entity.Member;
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
                            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXISTS);
                        });

        memberRepository.save(MemberMapper.toEntity(memberDto));
    }

    public String authenticate(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.getPassword()
                   .equals(memberDto.password())) {
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }
        
        return jwtUtil.generateToken(member.getId());
    }

}
