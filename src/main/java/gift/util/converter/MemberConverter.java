package gift.util.converter;

import gift.dto.MemberDTO;
import gift.entity.Member;

public class MemberConverter {

    public static MemberDTO convertToMemberDTO(Member member) {
        return new MemberDTO(member.getEmail(), member.getPassword(), member.getName(),
                member.getRole(), member.getWishList());
    }

    public static Member convertToMember(MemberDTO memberDTO) {
        return new Member(memberDTO.getEmail(), memberDTO.getPassword(), memberDTO.getName(), memberDTO.getRole(), memberDTO.getWishList());
    }
}
