package gift.mapper;

import gift.auth.DTO.MemberDTO;
import gift.model.member.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDTO toMemberDTO(MemberEntity memberEntity) {
        return new MemberDTO(
            memberEntity.getId(),
            memberEntity.getEmail(),
            memberEntity.getPassword()
        );
    }

    public MemberEntity toMemberEntity(MemberDTO memberDTO) {
        var memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setPassword(memberDTO.getPassword());
        return memberEntity;
    }
}
