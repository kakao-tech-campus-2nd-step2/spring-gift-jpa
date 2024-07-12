package gift.token;

import gift.member.MemberDTO;

public class MemberTokenDTO {

    private String email;

    public static MemberTokenDTO fromMemberDTO(MemberDTO memberDTO) {
        return new MemberTokenDTO(memberDTO.getEmail());
    }

    public MemberTokenDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
