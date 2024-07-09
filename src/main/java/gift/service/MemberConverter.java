package gift.service;

import gift.dto.MemberDTO;
import gift.entity.Member;

public class MemberConverter {
    public static MemberDTO convertToMemberDTO(Member member) {
        return new MemberDTO(member.getEmail(), member.getPassword(), member.getName(),
                member.getRole(), member.getWishList());
    }
}
