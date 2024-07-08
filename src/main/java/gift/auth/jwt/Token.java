package gift.auth.jwt;

import jakarta.validation.constraints.NotBlank;

public record Token(@NotBlank String token) {}
