package gift.member.service;

import static gift.member.Role.USER;

import gift.auth.token.AuthToken;
import gift.auth.token.AuthTokenGenerator;
import gift.member.Member;
import gift.member.dto.MemberReqDto;
import gift.member.dto.MemberResDto;
import gift.member.exception.MemberAlreadyExistsByEmailException;
import gift.member.exception.MemberCreateException;
import gift.member.exception.MemberDeleteException;
import gift.member.exception.MemberNotFoundByIdException;
import gift.member.exception.MemberUpdateException;
import gift.member.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthTokenGenerator authTokenGenerator;

    public MemberService(MemberRepository memberRepository, AuthTokenGenerator authTokenGenerator) {
        this.memberRepository = memberRepository;
        this.authTokenGenerator = authTokenGenerator;
    }

    public List<MemberResDto> getMembers() {
        return memberRepository.findMembers().stream()
                .map(MemberResDto::new)
                .toList();
    }

    public MemberResDto getMember(Long memberId) {
        Member findMember = memberRepository.findMemberByIdOrThrow(memberId);
        return new MemberResDto(findMember);
    }

    public String getMemberPassword(Long memberId) {
        Member findMember = memberRepository.findMemberByIdOrThrow(memberId);
        return findMember.getPassword();
    }

    public AuthToken register(MemberReqDto memberReqDto) {
        checkDuplicateEmail(memberReqDto.email());  // 중복되는 이메일이 있으면 예외 발생

        // 일반 사용자로 회원 가입
        // 관리자 계정은 데이터베이스에서 직접 추가
        Long memberId;
        try {
            memberId = memberRepository.addMember(memberReqDto, USER.getValue());
        } catch (Exception e) {
            throw MemberCreateException.EXCEPTION;
        }

        Member newMember = memberRepository.findMemberByIdOrThrow(memberId);

        return authTokenGenerator.generateToken(new MemberResDto(newMember));
    }

    public void updateMember(Long memberId, MemberReqDto memberReqDto) {
        validateMemberExistsById(memberId);
        try {
            memberRepository.updateMemberById(memberId, memberReqDto);
        } catch (Exception e) {
            throw MemberUpdateException.EXCEPTION;
        }
    }

    public void deleteMember(Long memberId) {
        validateMemberExistsById(memberId);
        try {
            memberRepository.deleteMemberById(memberId);
        } catch (Exception e) {
            throw MemberDeleteException.EXCEPTION;
        }
    }

    private void validateMemberExistsById(Long memberId) {
        boolean isExist = memberRepository.isMemberExistById(memberId);

        if (!isExist) {
            throw MemberNotFoundByIdException.EXCEPTION;
        }
    }

    private void checkDuplicateEmail(String email) {
        boolean isExist = memberRepository.isMemberExistByEmail(email);

        if (isExist) {
            throw MemberAlreadyExistsByEmailException.EXCEPTION;
        }
    }
}
