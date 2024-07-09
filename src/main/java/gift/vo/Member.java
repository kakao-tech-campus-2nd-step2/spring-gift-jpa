package gift.vo;

public class Member {
    private String email;
    private String password;
    private MemberRole role;

    public Member(String email, String password) {
        this(email, password, String.valueOf(MemberRole.USER));
    }

    public Member(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = MemberRole.valueOf(role);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public MemberRole getRole() {
        return role;
    }

    /**
     * 객체와 매개변수로 받은 email이 일치하는지 검증하는 메소드
     * @param email 검증할 email
     */
    public void validateEmail(String email) {
        if (!this.email.equals(email)) {
            throw new RuntimeException("입력하신 이메일로 가입된 회원이 존재하지 않습니다.");
        }
    }
}
