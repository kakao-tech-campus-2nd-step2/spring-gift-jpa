package gift.dto.member;

public record MemberEncryptedDTO(
        String email,
        String encryptedPW
) { }
