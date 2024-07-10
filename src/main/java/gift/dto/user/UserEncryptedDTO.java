package gift.dto.user;

public record UserEncryptedDTO(
        String email,
        String encryptedPW
) { }
