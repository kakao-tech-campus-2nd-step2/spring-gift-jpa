package gift.dto.member;

public record EncryptedUpdateDTO(
        long id,
        String encryptedPw
) {
}
