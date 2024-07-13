package gift.service;

import gift.auth.DTO.MemberDTO;
import gift.mapper.MemberMapper;
import gift.model.member.MemberEntity;
import gift.model.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 사용자 존재 여부 확인 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 존재 여부
     */
    public boolean isExist(MemberDTO memberDTO) {
        return memberRepository.existsByEmailAndPasswordAndDeleteFalse(memberDTO.getEmail(),
            memberDTO.getPassword());
    }

    /**
     * 사용자 회원가입 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 존재 여부
     */
    public boolean signUp(MemberDTO memberDTO) {
        var member = memberMapper.toMemberEntity(memberDTO, false);
        memberRepository.save(member);
        return true;
    }

    /**
     * 사용자 ID 조회 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 ID
     */
    public long getUserId(MemberDTO memberDTO) {
        MemberEntity member = memberRepository.findByEmailAndPasswordAndDeleteFalse(
            memberDTO.getEmail(), memberDTO.getPassword());
        return member != null ? member.getId() : -1;
    }
}
