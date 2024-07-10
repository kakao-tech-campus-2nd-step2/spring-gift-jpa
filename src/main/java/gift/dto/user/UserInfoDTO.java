package gift.dto.user;

public record UserInfoDTO(
        long id,
        String email,
        String encryptedPw
) {

}
