package gift.main.dto;

public class UserVo {
    private final String name;
    private final String email;
    private final String role;

    public UserVo(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
