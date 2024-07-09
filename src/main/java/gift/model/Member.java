package gift.model;

import gift.common.enums.Role;

public class Member {
    private Long id;
    private String email;
    private String password;
    private Role role;

    public Member(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
