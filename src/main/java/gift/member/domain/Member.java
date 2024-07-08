package gift.member.domain;

public record Member(
        Long id,
        String email,
        String password) { }
