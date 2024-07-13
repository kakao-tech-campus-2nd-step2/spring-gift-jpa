package gift.main.dto;


public class UserDto {
    private final String name;
    private final String email;
    private final String password;
    private final String role;


    public UserDto(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDto(UserJoinRequest userJoinRequest) {
        this.name = userJoinRequest.name();
        this.email = userJoinRequest.email();
        this.password = userJoinRequest.password();
        this.role = userJoinRequest.role();
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

    public String getRole() {
        return role;
    }

}
