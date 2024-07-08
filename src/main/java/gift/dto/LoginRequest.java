package gift.dto;

import jakarta.validation.constraints.Email;

public record LoginRequest(@Email(message = "이메일 형식의 입력이어야 합니다.") String email, String password) {
}
