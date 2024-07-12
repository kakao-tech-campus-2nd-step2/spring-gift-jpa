package gift.member;

import gift.token.MemberTokenDTO;

public class MemberDTO {

    private String email;
    private String password;

    public MemberDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Member toEntity() {
        return new Member(email, password);
    }

    public MemberTokenDTO toTokenDTO() {
        return new MemberTokenDTO(email);
    }
}
