package gift.domain.service;

import gift.domain.dto.request.MemberRequest;
import gift.domain.dto.response.MemberResponse;
import gift.domain.entity.Member;
import gift.domain.exception.MemberAlreadyExistsException;
import gift.domain.exception.MemberIncorrectLoginInfoException;
import gift.domain.exception.MemberNotFoundException;
import gift.domain.repository.MemberRepository;
import gift.global.util.HashUtil;
import gift.global.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public MemberResponse registerUser(MemberRequest requestDto) {

        // 기존 회원인 경우 예외
        memberRepository.findByEmail(requestDto.email()).ifPresent(p -> {
            throw new MemberAlreadyExistsException();
        });

        return new MemberResponse(jwtUtil.generateToken(memberRepository.save(requestDto.toEntity("member"))));
    }

    @Transactional(readOnly = true)
    public MemberResponse loginUser(MemberRequest requestDto) {
        // 존재하지 않은 이메일을 가진 유저로 로그인 시도
        // 존재한 경우 user 참조 반환
        Member member = memberRepository.findByEmail(requestDto.email())
            .orElseThrow(MemberIncorrectLoginInfoException::new);

        // 유저는 존재하나 비밀번호가 맞지 않은 채 로그인 시도
        if (!HashUtil.hashCode(requestDto.password()).equals(member.getPassword())) {
            throw new MemberIncorrectLoginInfoException();
        }

        return new MemberResponse(jwtUtil.generateToken(member));
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
}
