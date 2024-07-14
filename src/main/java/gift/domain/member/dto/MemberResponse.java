package gift.domain.member.dto;

public class MemberResponse {

    String email;
    String password;

    public MemberResponse() {
    }

    public MemberResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
