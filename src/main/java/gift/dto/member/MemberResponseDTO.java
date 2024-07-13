package gift.dto.member;

public record MemberResponseDTO(
        long id,
        String email
) {
    public MemberResponseDTO {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
    }
}
