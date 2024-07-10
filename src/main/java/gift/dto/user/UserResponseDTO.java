package gift.dto.user;

public record UserResponseDTO(
        long id,
        String email
) {
    public UserResponseDTO {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
    }
}
