package gift;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {
    private Long memberId;
    private String email;
    private String password;
    private String role;

    public MemberDto(Long memberId, String email, String password, String role) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
