package gift.main.dto;

import gift.main.entity.Role;

public class UserDto {
    private final String name;
    private final String email;
    private final String password;
    private final Role role;


    public UserDto(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.toRole(role);
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

    public Role getRole() {
        return role;
    }
}
