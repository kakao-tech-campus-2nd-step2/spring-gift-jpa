package gift.controller.member.dto;

import gift.model.member.Member;

public class MemberResponse {

    public record Login(String token) {

        public static Login from(String token) {
            return new Login(token);
        }
    }

    public record Info(
        String email,
        String name
    ) {

        public static Info from(Member member) {
            return new Info(member.getEmail(), member.getName());
        }
    }
}
