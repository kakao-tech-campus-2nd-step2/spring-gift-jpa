package gift.dto;

import gift.domain.Member;

public class MemberRequestDto {
    private String email;
    private String password;

    public Member toEntity(){
        return new Member(this.getEmail(), this.getPassword());
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
