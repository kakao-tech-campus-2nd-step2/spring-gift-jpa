package gift.member.domain;

public class Member {
    private String email;
    private String password;

    public Member(String email, String password) {
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
