package gift.service;

import gift.auth.DTO.MemberDTO;
import gift.model.member.MemberEntity;
import gift.model.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 사용자 존재 여부 확인 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 존재 여부
     */
    public boolean isExist(MemberDTO memberDTO) {
        return memberRepository.existsByEmailAndPasswordAndDeleteFalse(memberDTO.getEmail(), memberDTO.getPassword());
    }

    /**
     * 사용자 회원가입 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 존재 여부
     */
    public boolean signUp(MemberDTO memberDTO) {
        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setPassword(memberDTO.getPassword());
        return memberRepository.save(member) != null;
    }

    /**
     * 사용자 ID 조회 메서드
     *
     * @param memberDTO 로그인 정보
     * @return 사용자 ID
     */
    public long getUserId(MemberDTO memberDTO) {
        MemberEntity member = memberRepository.findByEmailAndPasswordAndDeleteFalse(memberDTO.getEmail(), memberDTO.getPassword());
        return member != null ? member.getId() : -1;
    }
}
