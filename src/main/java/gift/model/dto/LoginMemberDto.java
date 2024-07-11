package gift.model.dto;

import gift.model.Member;

public class LoginMemberDto {

    Long id;
    String name;
    String email;
    String role;

    public LoginMemberDto(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

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

    public Member toEntity() {
        return new Member(
            id,
            email,
            name,
            role
        );
    }
}
