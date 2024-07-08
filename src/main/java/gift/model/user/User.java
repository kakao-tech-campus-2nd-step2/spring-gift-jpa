package gift.model.user;

public class User {
    private final Long id;
    private final String email;
    private final String passWord;

    public User(Long id,String email,String passWord) {
        this.id = id;
        this.passWord = passWord;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmail() {
        return email;
    }
}