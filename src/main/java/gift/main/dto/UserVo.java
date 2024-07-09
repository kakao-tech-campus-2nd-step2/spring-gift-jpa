package gift.main.dto;

public class UserVo {

    private final Long id;

    private final String name;
    private final String email;
    private final String role;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public UserVo(Long id, String name, String email, String role) {
        this.id = id;

        this.name = name;
        this.email = email;
        this.role = role;
    }
}
