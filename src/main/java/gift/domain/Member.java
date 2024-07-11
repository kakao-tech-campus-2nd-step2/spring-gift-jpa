package gift.domain;

public class Member {

    private Long id;
    private String email;
    private String password;

    public Member() {
    }

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
