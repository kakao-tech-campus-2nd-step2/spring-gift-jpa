package gift.api.member;

public class Member {

    private Long id;
    private String email;
    private String password;
    private Role role;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", role=" + role +
            '}';
    }
}
