package gift.user.model.dto;

public record User(Long id, String email, String password, String role, String salt) {
}