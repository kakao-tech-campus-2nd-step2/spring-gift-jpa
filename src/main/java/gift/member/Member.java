package gift.member;

public class Member {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean role;

    public Member(String id, String name, String email, String password, boolean role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRole() {
        return role;
    }
}

