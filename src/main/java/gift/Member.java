package gift;

public class Member {
    private Long memberId;
    private String email;
    private String password;
    private String role;

    public Member(Long memberId, String email, String password, String role) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
