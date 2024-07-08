package gift.dto;

import gift.domain.User;

public class UserResponseDto {
    private Long id;
    private String email;

    public UserResponseDto(String email) {
        this.email = email;
    }

    public UserResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserResponseDto from(final User user){
        return new UserResponseDto(user.getId(), user.getEmail());
    }
}
