package gift.user;

public class User {
    Long id;
    String email;
    String password;
    String nickname;

    public User() {
    }


    public User(Long id, String email, String password, String nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickName;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
