package gift.controller.user.dto;

import gift.model.member.Member;

public class MemberResponse {

    public record LoginResponse(String token) {

        public static LoginResponse from(String token) {
            return new LoginResponse(token);
        }
    }

    public record InfoResponse(
        String email,
        String name
    ) {

        public static InfoResponse from(Member member) {
            return new InfoResponse(member.getEmail(), member.getName());
        }
    }
}
