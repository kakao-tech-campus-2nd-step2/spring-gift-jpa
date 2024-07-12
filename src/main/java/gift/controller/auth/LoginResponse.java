package gift.controller.auth;

import java.util.UUID;

public record LoginResponse(UUID id, String email, String grade) {

    public boolean isAdmin() {
        return grade.equals("admin");
    }
}
