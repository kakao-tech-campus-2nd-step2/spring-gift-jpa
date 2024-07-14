package gift.service;

import static gift.util.Constants.EMAIL_ALREADY_USED;
import static gift.util.Constants.ID_NOT_FOUND;
import static gift.util.Constants.INVALID_CREDENTIALS;

import gift.dto.member.MemberRequest;
import gift.dto.member.MemberResponse;
import gift.exception.member.EmailAlreadyUsedException;
import gift.exception.member.ForbiddenException;
import gift.model.Member;
import gift.repository.MemberRepository;
import gift.util.JWTUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입 (회원 추가)
    public MemberResponse registerMember(MemberRequest memberDTO) {
        if (memberRepository.existsByEmail(memberDTO.email())) {
            throw new EmailAlreadyUsedException(EMAIL_ALREADY_USED);
        }

        Member member = new Member(null, memberDTO.email(), memberDTO.password());
        Member savedMember = memberRepository.save(member);

        String token = jwtUtil.generateToken(savedMember.getId(), member.getEmail());
        return new MemberResponse(savedMember.getId(), savedMember.getEmail(), token);
    }

    // 로그인 (회원 검증)
    public MemberResponse loginMember(MemberRequest memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.email())
            .orElseThrow(() -> new ForbiddenException(INVALID_CREDENTIALS));

        if (!member.isPasswordMatching(memberDTO.password())) {
            throw new ForbiddenException(INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(member.getId(), member.getEmail());
        return new MemberResponse(member.getId(), member.getEmail(), token);
    }

    // 모든 회원 조회
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
            .map(MemberService::convertToDTO)
            .collect(Collectors.toList());
    }

    // ID로 회원 조회
    public MemberResponse getMemberById(Long id) {
        return memberRepository.findById(id)
            .map(MemberService::convertToDTO)
            .orElseThrow(() -> new ForbiddenException(INVALID_CREDENTIALS));
    }

    // 회원 수정
    public MemberResponse updateMember(Long id, MemberRequest memberDTO) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new ForbiddenException(INVALID_CREDENTIALS));

        boolean emailChanged = !member.isEmailMatching(memberDTO.email());
        boolean emailAlreadyUsed = memberRepository.existsByEmail(memberDTO.email());

        if (emailChanged && emailAlreadyUsed) {
            throw new EmailAlreadyUsedException(EMAIL_ALREADY_USED);
        }

        member.update(memberDTO.email(), memberDTO.password());
        Member updatedMember = memberRepository.save(member);
        return convertToDTO(updatedMember);
    }

    // 회원 삭제
    public void deleteMember(Long id) throws ForbiddenException {
        if (!memberRepository.existsById(id)) {
            throw new ForbiddenException(ID_NOT_FOUND);
        }
        memberRepository.deleteById(id);
    }

    // Mapper methods
    public Member convertToEntity(MemberResponse memberResponse) {
        return new Member(memberResponse.id(), memberResponse.email(), null);
    }

    private static MemberResponse convertToDTO(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), null);
    }
}
