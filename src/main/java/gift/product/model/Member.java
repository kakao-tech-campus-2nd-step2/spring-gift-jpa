package gift.product.model;

public class Member {

    private final Long id;
    private final String email;
    private final String password;

    public Member(String email, String password) {
        this(null, email, password);
    }

    public Member(Long memberId, String email, String password) {
        this.id = memberId;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
