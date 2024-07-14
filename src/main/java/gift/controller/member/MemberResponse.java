package gift.controller.member;

import java.util.UUID;

public record MemberResponse(UUID id, String email, String password, String grade) {

}