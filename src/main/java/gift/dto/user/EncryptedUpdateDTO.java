package gift.dto.user;

public record EncryptedUpdateDTO(
        long id,
        String encryptedPw
) {
}
