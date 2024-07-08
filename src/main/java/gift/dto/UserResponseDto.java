package gift.dto;

public class UserResponseDto {
    public final Long id;
    public final String email;

    public UserResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
