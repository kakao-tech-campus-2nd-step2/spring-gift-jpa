package gift.member;

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
}
